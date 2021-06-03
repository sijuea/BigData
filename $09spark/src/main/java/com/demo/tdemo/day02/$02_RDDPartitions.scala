package com.demo.tdemo.day02

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class $02_RDDPartitions {


  val sc = new SparkContext(new SparkConf().setMaster("local[3]").setAppName("test")/*.set("spark.default.parallelism","50")*/)

  /**
    * 根据本地集合创建的RDD分区
    *     1、如果有设置分区数,分区数 = 自己设置的分区个数
    *     2、如果没有设置
    *         1、有设置spark.default.parallelism参数,分区数默认=spark.default.parallelism参数的值
    *         2、没有设置spark.default.parallelism参数
    *             1、本地模式: 分区数默认 = local[N]里面的N
    *             2、集群模式: 分区数默认 = math.max(所有executor cpu总核数, 2)
    */
  @Test
  def collectionRddPartitions(): Unit ={

    val rdd = sc.parallelize(List(10,3,6,8,2,10,40))

    rdd.mapPartitionsWithIndex((index,it)=>{
      println(s"分区号=${index}  分区数据=${it.toList}")
      it
    }).collect()
    //查看分区数
    println(rdd.partitions.length)

    Thread.sleep(100000)
  }

  /**
    * 根据读取文件创建RDD分区数:
    *     1、有设置分区数，rdd分区数>= 指定分区数
    *     2、没有设置分区数,rdd分区数>= math.min(defaultParallelism, 2)
    */
  @Test
  def fileRddPartitions(): Unit ={

    val rdd = sc.textFile("datas/wc.txt",4)
    println(rdd.partitions.length)
  }

  /**
    * 根据其他rdd衍生的RDD的分区数,默认=父RDD的分区数
    */
  @Test
  def rddPartitions(): Unit ={
    val rdd1 = sc.textFile("datas/wc.txt",4)

    val rdd2 = rdd1.flatMap(_.split(" "))

    println(rdd1.partitions.length)
    println(rdd2.partitions.length)
  }

}
