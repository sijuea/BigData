package com.demo.app

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDate, LocalDateTime, ZoneId}
import java.util

import com.alibaba.fastjson.JSON
import com.demo.app.DAUApp.{process,appName,internal}
import com.demo.app.GMVApp.ssc
import com.demo.constants.CommonConstants
import com.demo.entity.{CouponAlertInfo, EventLog}
import com.demo.utils.{MyEsUtil, MykafkaUtil}
import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.expressions.{Minute, Second}
import org.apache.spark.sql.catalyst.util.DateFormatter
import org.apache.spark.streaming.{Duration, Minutes, Seconds, StreamingContext}

import scala.util.control.Breaks._

/**
 * @author sijue
 * @date 2021/6/1 16:16
 * @describe
 */
object WarnApp extends BaseApp {

  override var appName: String = "WarnApp"
  override var internal: Int = 10

  def main(args: Array[String]): Unit = {
    ssc = new StreamingContext("local[4]", appName, Seconds(internal))
    process {
      //从kafakaEvENT_LOG读取数据
      //读取kafka数据
      val rdd1 =
      MykafkaUtil.getKafkaStream(CommonConstants.KAFKA_EVENT_LOG, ssc)
      //解析kafka数据，获取kafka数据的value值
      val rdd2 = rdd1.map(x => {
        val json = x.value();
        val log = JSON.parseObject(json, classOf[EventLog])
        //基于ts获取logDate、logHour
        val ts =
          LocalDateTime.ofInstant(Instant.ofEpochMilli(log.ts), ZoneId.of("Asia/Shanghai"))
        //将ts转换为指定格式
        val ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        log.logDate = ts.format(ofPattern)
        log.logHour = ts.getHour + ""
        log
      })
      //取rdd2前5分钟的值，转换为key-value值，然后group by
      //key（mid，userid）value(log)
      val rdd3 = rdd2.window(Minutes(5)).map(param => {
        ((param.mid, param.uid), param)
      }).groupByKey()
      //对rdd3的value值进行判断处理
      rdd3.count().print(10000)
      val rdd4 = rdd3.map {
        case (key, value) => {
          var flag = false;
          breakable {
            value.foreach(log => {
              //判断只要有浏览商品行为就返回false
              if (log.evid.equals("clickItem")) {
                flag = false;
                break()
              } else if (log.evid.equals("coupon")) {
                flag = true;
                //加上break表示多条数据中，只要有优惠券的数据就是预警，不加break，表示多条数据的行为都是领券
                //此处不加【表示全部数据都是领取优惠券预警】
                //              break();
              }
            })

          }
          if (flag) {
            (key._1, value)
          } else {
            //?????
            (null,null)
          }
        }
      }
      rdd4.count().print(10000)
      //然后对rdd4排除空值，然后进行分组，筛选分组之后的value的size>=3
      val rdd5 = rdd4.filter(x=>x._1!=null).groupByKey().filter {
        case (key, value) => value.size >= 3
      }.mapValues(value => value.flatten) //将得出的数据  {mid1:{log1,log2},mid1:{log3,log3}...}进行扁平化处理

      //将得出的数据转换为预警样例类
      val rdd6 = rdd5.map { case (key, list) => {
        val uids = new util.HashSet[String]
        val itemIds = new util.HashSet[String]
        val events = new util.ArrayList[String]
        list.foreach(param => {
          //变量param封装set，list
          uids.add(param.uid)
          itemIds.add(param.itemid)
          events.add(param.evid)
        })
        CouponAlertInfo(key, uids, itemIds, events, System.currentTimeMillis())
      }
      }
      //封装docList(key{es表中的主键}【时间+mid】，value：List<预警样例类>)
      val docList = rdd6.map(x => {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        val dateTime = LocalDateTime.ofInstant(
          Instant.ofEpochMilli(x.ts), ZoneId.of("Asia/Shanghai"))
        val date_id = dateTime.format(pattern)
        (x.mid + "_" + date_id, x)
      })
      //将得出的rdd发送到ES
      docList.foreachRDD(rdd => {
        rdd.foreachPartition(param =>
          MyEsUtil.insertBulk("gmall_coupon_alert"+ LocalDate.now(), param.toList)
        )
      })

    }
  }


}
