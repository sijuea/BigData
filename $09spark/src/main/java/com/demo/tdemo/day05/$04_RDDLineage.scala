package com.demo.tdemo.day05

import org.apache.spark.{SparkConf, SparkContext}

object $04_RDDLineage {

  /**
    * toDebugString可以打印RDD的血统
    *
    */
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    val rdd1 = sc.textFile("datas/wc.txt")
    println(rdd1.toDebugString)
    val rdd2 = rdd1.flatMap(_.split(" "))
    println("_"*100)
    println(rdd2.toDebugString)
    val rdd3 = rdd2.map((_,1))
    println("_"*100)
    println(rdd3.toDebugString)
    val rdd4 = rdd3.reduceByKey(_+_)
    println("_"*100)
    println(rdd4.toDebugString)
    println(rdd4.collect().toList)
  }
}
