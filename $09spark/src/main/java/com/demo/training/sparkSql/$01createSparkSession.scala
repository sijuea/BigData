package com.demo.training.sparkSql

import org.apache.spark.sql.SparkSession

/**
 * @author sijue
 * @date 2021/4/29 9:52
 * @describe
 */
object $01createSparkSession {
  def main(args: Array[String]): Unit = {
    //方式一：常用
    val spark =
      SparkSession.builder().master("local[4]").appName("test").getOrCreate()

    //方式二：
    val spark1 = new SparkSession.Builder().master("local[4]").appName("test").getOrCreate()

  }



}
