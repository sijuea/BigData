package com.demo.app
import java.time.{Instant, LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter
import java.util.Properties

import com.alibaba.fastjson.JSON
import com.demo.constants.CommonConstants
import com.demo.entity.StartUpLog
import com.demo.utils.{MykafkaUtil, PropertiesUtil}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.spark.SparkContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import redis.clients.jedis.Jedis
//导入phoenix提供的各种静态方法
import org.apache.phoenix.spark._
/**
 * @author sijue
 * @date 2021/5/31 18:50
 * @describe
 */
object DAUApp  extends BaseApp {
  override var appName: String = "DAUApp"
  override var internal: Int = 10
  //读取配置文件
  private val properties: Properties = PropertiesUtil.load("config.properties")


  def main(args: Array[String]): Unit = {
    ssc =
      new StreamingContext("local[4]",appName,Seconds(internal))

      process {
      //读取kafka数据
      val rdd1 = MykafkaUtil.getKafkaStream(CommonConstants.KAFKA_START_LOG, ssc)
      //解析kafka数据，获取kafka数据的value值
      val rdd2 = rdd1.map(x => {
        val json = x.value();
        val value = JSON.parseObject(json, classOf[StartUpLog])
        //设置log_date和log_hour
        val formatter1: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        //将时间戳转换为日期格式
        val time = LocalDateTime.ofInstant(
          Instant.ofEpochMilli(value.ts), ZoneId.of("Asia/Shanghai"))
        //将日期格式转换为指定格式
        value.logDate = time.format(formatter1)
        //赋值hour
        value.logHour = time.getHour + ""
        value
      })
      rdd2.count().print()
      //对当前rdd进行去重;但是本身的streaming的rdd不能去重，所以需要使用transform将当前rdd转换为sparkCore的算子
      val rdd3 = rdd2.transform(rdd => {
        //将rdd转换为key-value格式，key：date+"_"+mid value:对象
        val mapRdd = rdd.map(param => {
          val key = param.logDate + "_" + param.mid
          (key, param)
        })
        //group bykey
        val groupRDD = mapRdd.groupByKey()
        //然后对分组之后的数据进行排序获取第一条
        val logRdd = groupRDD.flatMap {
          case (key, value) => {
            val logs = value.toList.sortBy(x => x.ts).take(1)
            logs
          }
        }
        logRdd
      })
      rdd3.count().print()
      //以下三个job都是对rdd3的操作，可以先把rdd3缓存以下
      rdd3.cache()
      //获取redis的数据，对rdd3的数据进行去重 优化的两个方式
      //方式一：每个分区建立一个连接，读取
      removeDuplicateLog1(rdd3)
      //方式二：建立一次连接，请求redis的数据，然后将数据广播【注意数据闭包的时候传入的对象是否序列化】

      //将数据写入redis Jedis不能实现闭包，是因为Jedis不能序列化
      rdd3.foreachRDD(rdd => {
        rdd.foreachPartition(partition=>{
          val jedis = new Jedis(
            properties.getProperty("redis.host"), properties.getProperty("redis.port").toInt)
          partition.foreach(x => jedis.sadd("DAU:" + x.logDate, x.mid))
        })

      }
      )
      //将数据写入Hbase saveToPhoenix
      rdd3.foreachRDD(rdd => {
        rdd.saveToPhoenix("GMALL2021_DAU",
          Seq("MID", "UID", "APPID", "AREA", "OS", "CH", "TYPE", "VS", "LOGDATE", "LOGHOUR", "TS"),
          HBaseConfiguration.create(),
          Some("hadoop102:2181"))
      })
    }
  }

  /**
   * 数据批的每个分区创建一次redis连接，获取redis的数据，然后查看rdd3数据是否存在set集合，然后去重、
   * 在redis中判断
   * @param rdd3
   */
  def removeDuplicateLog1(rdd3: DStream[StartUpLog])= {
    //因为mapPartition存在sparkCore的算子，并且此处直接过滤rdd3的数据，没有返回值，
    // 所以先使用foreachRDD转换为rdd算子
    //然后再rdd算子中调用mapPartition
      rdd3.foreachRDD(rdd => {
      rdd.mapPartitions(partiiton => {
        val jedis = new Jedis(
          properties.getProperty("redis.host"), properties.getProperty("redis.port").toInt)
        val filter = partiiton.filter(x => {
          val key = "DAU:" + LocalDateTime.now()
          jedis.sismember(key,x.mid)
        })
        filter
      })
    })

  }


  /**
   * 建立一次连接，请求redis的数据，然后将数据广播【注意数据闭包的时候传入的对象是否序列化】
   * 判断是在spark中执行
   * @param rdd3
   */
  def removeDuplicateLog2(rdd3: DStream[StartUpLog])= {
    rdd3.foreachRDD(rdd=>{
      val jedis = new Jedis(
        properties.getProperty("redis.host"), properties.getProperty("redis.port").toInt)
      val key = "DAU:" + LocalDateTime.now()
      val set = jedis.smembers(key)
      val broad = ssc.sparkContext.broadcast(set)
      rdd.mapPartitions(partition=>{
        partition.filter(sub=>{!broad.value.contains(sub.mid)})
      })
    })
  }


  }
