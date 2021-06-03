package com.demo.tdemo.day07

import org.apache.spark
import org.apache.spark.sql
import org.apache.spark.sql.SparkSession

object $01_SparkSession {

  def main(args: Array[String]): Unit = {

    //sparksession入口
    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()


    val sparkSession = new SparkSession.Builder().master("local[4]").appName("test").getOrCreate()

  }
}
