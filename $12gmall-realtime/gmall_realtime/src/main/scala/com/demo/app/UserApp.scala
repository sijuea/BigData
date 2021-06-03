package com.demo.app

import java.util.Properties

import com.alibaba.fastjson.JSON
import com.demo.app.DAUApp.{appName, internal, process, properties, ssc}
import com.demo.constants.CommonConstants
import com.demo.entity.UserInfo
import com.demo.utils.{MykafkaUtil, PropertiesUtil}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.Jedis

/**
 * @author sijue
 * @date 2021/6/2 9:56
 * @describe
 */
object UserApp extends BaseApp {
  override var appName: String = "UserApp"
  override var internal: Int = 10
  private val properties: Properties = PropertiesUtil.load("config.properties")

  def main(args: Array[String]): Unit = {
    ssc =
      new StreamingContext("local[4]", appName, Seconds(internal))

    process {
      //读取kafka数据【USER_INFO】
      val ds = MykafkaUtil.getKafkaStream(CommonConstants.KAFKA_USER_INFO, ssc)
      //将user_info写入redis
      val ds1 = ds.map(_.value())
      //将ds1转换为rdd然后再rdd1按照分区将数据写入redis【key："user"+${user_id},value:user对象的json字符串】
      ds1.count().print(10000000)
      ds1.foreachRDD(rdd => {
        rdd.foreachPartition(partition => {
          val jedis = new Jedis(
            properties.getProperty("redis.host"), properties.getProperty("redis.port").toInt)
          partition.foreach(x => {
            val info = JSON.parseObject(x, classOf[UserInfo])
            jedis.set("user:" + info.id, x)
          })
        })
      })
    }
  }
}
