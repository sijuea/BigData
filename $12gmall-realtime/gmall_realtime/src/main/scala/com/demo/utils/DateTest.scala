package com.demo.utils

import java.text.SimpleDateFormat
import java.time.{Instant, LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter
import java.util.Date


/**
 * @author sijue
 * @date 2021/5/31 18:40
 * @describe
 */
object DateTest {
  def main(args: Array[String]): Unit = {

    val simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd")
    val simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH")

    val date = new Date(System.currentTimeMillis())

    println(simpleDateFormat1.format(date))
    println(simpleDateFormat2.format(date))


    println("---------------------------")

    val formatter1: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatter2: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH")

    val localDateTime: LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.of("Asia/Shanghai"))

    println(localDateTime.format(formatter1))
    println(localDateTime.format(formatter2))

    println(localDateTime.getHour)



  }

}
