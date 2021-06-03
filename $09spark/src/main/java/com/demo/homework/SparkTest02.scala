package com.demo.homework

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Try

/**
 * @author sijue
 * @date 2021/4/28 18:12
 * @describe
 */
object SparkTest02 {

  /**
   * 之前农产品的数据：获取每个省份每个农产品的平均价格
   *
   * @param rdd1
   *
   */
  def getAvgPrice(rdd1: RDD[String]) = {
    var rdd2 = rdd1.filter(x => x.split("\t").length == 6)
    val rdd3 = rdd2.map(x => {
      val line = x.split("\t")
      (line(4), line(0), Try(line(1).toDouble).getOrElse(0.0))
    }).filter(_._3 != 0.0)
    val rdd4 = rdd3.groupBy { case (province, name, price) => (province, name) }
    rdd4.map(x => {
      val total = x._2.map(x => x._3).sum
      (x._1._1, (x._1._2, total / x._2.size))
    })
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("test02")
    val sc = new SparkContext(conf)
    val rdd1 = sc.textFile("datas/product.txt")
    println(getAvgPrice(rdd1).collect().toList)
  }

}
