package com.demo.app

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import java.util.Properties

import com.alibaba.fastjson.JSON
import com.demo.app.DAUApp.properties
import com.demo.app.UserApp.{appName, internal, process, ssc}
import com.demo.constants.CommonConstants
import com.demo.entity.{OrderDetail, OrderInfo, SaleDetail, UserInfo}
import com.demo.utils.{MyEsUtil, MykafkaUtil, PropertiesUtil}
import com.google.gson.Gson
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.Jedis

import scala.collection.mutable.ArrayBuffer

/**
 * @author sijue
 * @date 2021/6/2 10:10
 * @describe
 */
object OrderDetailApp  extends BaseApp {
  override var appName: String = "OrderDetailApp"
  override var internal: Int = 10
  private val properties: Properties = PropertiesUtil.load("config.properties")

  def main(args: Array[String]): Unit = {
    ssc =
      new StreamingContext("local[4]", appName, Seconds(internal))

    process {
      //读取kafka数据【USER_INFO】
      val orderInfoDs =MykafkaUtil.getKafkaStream(CommonConstants.KAFKA_ORDER_INFO, ssc)
      val orderDetailDs =
        MykafkaUtil.getKafkaStream(CommonConstants.KAFKA_ORDER_DETAIL, ssc)
      //将流的元素转换为key-value key:order_id  value:样例类
      val  ds1=orderInfoDs.map(
        param=> {
          val info = JSON.parseObject(param.value(), classOf[OrderInfo])
          //create_date 和create_hour赋值
          val ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          val date1 = LocalDateTime.parse(info.create_time, ofPattern);
          val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          info.create_date = date1.format(formatter);
          info.create_hour = date1.getHour() + "";
          //手机号脱敏
          //对手机号脱敏
          info.consignee_tel = info.consignee_tel.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
          (info.id,info)
        }
      )

      ds1.count().print(10000000)
     val ds2= orderDetailDs.map(param=>{
       val value=JSON.parseObject(param.value(),classOf[OrderDetail])
       (value.order_id,value)
     })
      //ds1和ds2进行join
      val ds3 = ds1.fullOuterJoin(ds2)
      //使用缓存解决fulljoin 中匹配不到的问题
      //以分区写入操作,
      ds3.count().print(100000)
      val rdd4 = ds3.mapPartitions(partition => {
        val jedis = new Jedis(
          properties.getProperty("redis.host"), properties.getProperty("redis.port").toInt)
        val gson = new Gson()
        val saleDetails = new ArrayBuffer[SaleDetail]()
        partition.foreach {
          case (key, (v1, v2)) => {
            //对option进行盘空
            if (v1 != None && v2 != None) {
              val info: OrderInfo = v1.get
              val detail = v2.get
              //1.匹配成功，生成结果
              saleDetails.append( new SaleDetail(info, detail))
              //设置order_info的失效时间[10分钟]
              jedis.setex("order_info:" + info.id, 60 * 2 * 5, gson.toJson(info))
            } else if (v1 == None && v2 != None) {
              //2.如果双流join，info=null，detail！=null，那么detail就取redis中找有没有对应的order_id
              // 2-1将order_info写入redis
              //order_info的order_id需要无条件写入redis，存储的数据是order_id，user_id
              //集合存储【set集合<order_id 拼接user_id>】
              // 或者<选择>单值存储【key:"orderInfo"${order_id},value:orderInfo.的json字符串】

              //2-2判断order_detail能否从redis获取对应的order_id
              val set = jedis.smembers("order_info:" + key)
              //遍历set拼接对象
              val detail=v2.get
              set.forEach(param => {
                saleDetails.append(new SaleDetail(gson.fromJson(param, classOf[OrderInfo]), detail))
              })
              //获取不到就将detail数据写入redis
              jedis.setex("order_detail:" + detail.order_id,
                5 * 2 * 60,
                gson.toJson(OrderDetail))
            } else {
              //order_info ！=null  &&oder_detail ==null
              //取redis中找看看有没order_detail，如果找到，就遍历set，拼接order_info和order_detail生成新对象
              val set = jedis.smembers("order_detail:" + key)
              //遍历set拼接对象
              val info=v1.get
              set.forEach(param => {
                saleDetails.append(new SaleDetail(info, gson.fromJson(param, classOf[OrderDetail])))
              })

            }

          }
        }
        saleDetails.iterator
      })
      rdd4.count().print(10000)
      //读取redis的用户信息然后补全
      val rdd5 = rdd4.mapPartitions(partition => {
        val jedis = new Jedis(
          properties.getProperty("redis.host"), properties.getProperty("redis.port").toInt)
        val saleDetail = partition.map(param => {
          val user = jedis.get("user:" + param.user_id)
            param.mergeUserInfo(new Gson().fromJson(user, classOf[UserInfo]))
          (param.order_detail_id,param)
        })
        saleDetail
      })
      //将数据写入ES
      rdd5.foreachRDD(rdd=>{
        rdd.foreachPartition(partition=> {
          MyEsUtil.insertBulk("gmall2021_sale_detail" +LocalDate.now(),partition.toList)
        })
      })


    }
  }
}
