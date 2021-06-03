package com.demo.tdemo.day03

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class $02_Transformation2 {

  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))


  /**
    * 交集
    */
  @Test
  def intersection(): Unit ={

    val rdd1 = sc.parallelize(List(1,2,3,4,5))

    val rdd3 = rdd1.map(x=>x*x)

    val rdd2 = sc.parallelize(List(4,5,6,7,8))

    val rdd4 = rdd2.map(x=>x*x)

    val rdd5 = rdd3.intersection(rdd4)
    println(rdd5.collect().toList)

    Thread.sleep(100000)
  }

  /**
    * 差集
    *
    */
  @Test
  def subtract(): Unit ={
    val rdd1 = sc.parallelize(List(1,2,3,4,5))
    val rdd2 = sc.parallelize(List(4,5,6,7,8))

    val rdd3 = rdd1.subtract(rdd2)


    println(rdd3.collect().toList)
  }


  @Test
  def union(): Unit ={
    val rdd1 = sc.parallelize(List(1,2,3,4,5))
    val rdd2 = sc.parallelize(List(4,5,6,7,8))

    val rdd3 = rdd1.union(rdd2)
    println(rdd3.dependencies)
    println(rdd3.partitions.length)

    println(rdd3.collect().toList)
  }

  /**
    *  zip: 拉链
    *   必须要求两个RDD的元素个数与分区数都一样
    */
  @Test
  def zip(): Unit ={
    val rdd1 = sc.parallelize(List("aa","bb","cc","dd"),2)
    val rdd2 = sc.parallelize(List(1,2,3,4))

    val rdd3 = rdd1.zip(rdd2)

    println(rdd3.collect().toList)

  }

}
