package com.demo.training.sparkSql

import org.apache.spark.sql.SparkSession

/**
 * @author sijue
 * @date 2021/4/29 11:13
 * @describe
 */
case class Person(name:String)
class $02createDataFrame {

    var spark=SparkSession.builder().master("local[4]").appName("test")

  /**
   * toDF：
   *  根据集合创建
   *  根据rdd创建
   */
  def createDF()={


  }
}
