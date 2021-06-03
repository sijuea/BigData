package com.demo.homework

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author sijue
 * @date 2021/4/28 21:35
 * @describe
 */
object SparkTest03 {
  def top3Click(list: RDD[String]) = {
    val rdd1 = list.map(x => {
      val line = x.split(" ")
      ((line(1), line.last), 1)
    })
    val rdd3 = rdd1.reduceByKey(_ + _)
    val rdd4 = rdd3.groupBy {
      case ((p, ad), num) => p
    }
    val rdd5 = rdd4.map {
      case (province, it) => {
        val rdd6 = it.toList.sortBy(_._2).reverse.take(3)
        val rdd7 = rdd6.map(x => (x._1._2, x._2))
        (province, rdd7)
      }
    }
    println(rdd5.collect().toList)
  }

  //需求：每个省份点击量前三的广告
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test03").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val rdd1 = sc.textFile("datas/agent.log")
    //时间戳 省份     最后一个是广告
    top3Click(rdd1)
  }
}
