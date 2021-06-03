package com.demo.tdemo.day06

import org.apache.spark.{SparkConf, SparkContext}

object Test {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val rdd = sc.parallelize(List( (2,3,4),(1,2,3),(1,1,2),(2,2,3)))

    val rdd2 = rdd.sortBy(x=>x)
    println(rdd2.collect().toList)
  }
}
