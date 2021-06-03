package com.demo.app

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId}

import com.alibaba.fastjson.JSON
import com.demo.app.DAUApp.{appName, internal, process, ssc}
import com.demo.app.WarnApp.internal
import com.demo.constants.CommonConstants
import com.demo.entity.OrderInfo
import com.demo.utils.MykafkaUtil
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.phoenix.spark.toProductRDDFunctions
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

/**
 * @author sijue
 * @date 2021/5/31 22:44
 * @describe
 */
object GMVApp  extends BaseApp {
  override var appName: String = "GMVApp"
  override var internal: Int = 10

  def main(args: Array[String]): Unit = {
    ssc =
      new StreamingContext("local[4]", appName,Seconds(internal))

    process {
      //读取kafka数据
      val rdd1 = MykafkaUtil.getKafkaStream(CommonConstants.KAFKA_ORDER_INFO, ssc)
      //解析kafka数据，获取kafka数据的value值
      val rdd2 = rdd1.map(x => {
        val json = x.value();
        val info = JSON.parseObject(json, classOf[OrderInfo])
        //给info的两个字段赋值以及对手机号进行脱敏
        //create_date 和create_hour赋值
        val ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        val date1 = LocalDateTime.parse(info.create_time, ofPattern);
        //赋值
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        info.create_date = date1.format(formatter);
        info.create_hour = date1.getHour() + "";
        //手机号脱敏
        //对手机号脱敏
        info.consignee_tel = info.consignee_tel.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        info
      }
      )
      //将数据写入phoenix
      //写入hbase
      rdd2.foreachRDD(rdd => {
        rdd.saveToPhoenix("GMALL2021_ORDER_INFO".toUpperCase,
          Seq("ID", "PROVINCE_ID", "CONSIGNEE", "ORDER_COMMENT", "CONSIGNEE_TEL", "ORDER_STATUS", "PAYMENT_WAY", "USER_ID", "IMG_URL", "TOTAL_AMOUNT", "EXPIRE_TIME", "DELIVERY_ADDRESS", "CREATE_TIME", "OPERATE_TIME", "TRACKING_NO", "PARENT_ORDER_ID", "OUT_TRADE_NO", "TRADE_BODY", "CREATE_DATE", "CREATE_HOUR"),
          HBaseConfiguration.create(),
          Some("hadoop102:2181")
        )
      })

    }
  }
}



