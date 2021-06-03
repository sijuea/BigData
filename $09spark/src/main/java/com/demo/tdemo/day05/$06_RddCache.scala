package com.demo.tdemo.day05

import org.apache.spark.{SparkConf, SparkContext}

object $06_RddCache {

  /**
    * RDD持久化:
    *     场景: 一个RDD在多个job中使用的时候
    *     作用: Rdd本身不存储数据,但是在执行job之后可以将rdd的数据保存起来,后续job再需要这个rdd数据的时候可以直接从存储机制中获取而不用重复计算得到
    *     RDD的持久化方式: 缓存、checkpoint
    *         1、缓存:
    *             1、存储位置: 存储在task所在的主机的内存或者本地磁盘中
    *             2、使用: val rdd2 = rdd.cache/ rdd.persist(..)
    *                 cache与persit的区别:
    *                     cache是将数据保存在内存中
    *                     persist可以自己指定存储级别
    *             3、存储级别:
    *                     NONE: 不做存储
    *                     DISK_ONLY: 将rdd数据持久化到本地磁盘
    *                     DISK_ONLY_2: 将rdd数据持久化到本地磁盘,保存两份
    *                     MEMORY_ONLY: 将rdd数据持久化到内存中
    *                     MEMORY_ONLY_2 : 将rdd数据持久化到内存中,保存两份
    *                     MEMORY_ONLY_SER: 将RDD数据序列化之后保存到内存中
    *                     MEMORY_ONLY_SER_2 : 将RDD数据序列化之后保存到内存中,保存两份
    *                     MEMORY_AND_DISK : 将RDD数据保存在内存中,如果内存不足一部分数据保存在磁盘中
    *                     MEMORY_AND_DISK_2: 将RDD数据保存在内存中,如果内存不足一部分数据保存在磁盘中,保存两份
    *                     MEMORY_AND_DISK_SER: 将RDD数据序列化之后保存在内存中,如果内存不足一部分数据保存在磁盘中
    *                     MEMORY_AND_DISK_SER_2: 将RDD数据序列化之后保存在内存中,如果内存不足一部分数据保存在磁盘中,保存两份
    *                     OFF_HEAP : 将RDD数据保存在堆外内存中
    *                     工作中常用的存储级别为: MEMORY_AND_DISK<一般用于数据量比较大的场景>、MEMORY_ONLY<一般用于数据量小的场景>
    *               shuffle会落盘,所以相当于shuffle算子自带有缓存
    *
    *
    */
  /*def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val rdd1 = sc.textFile("datas/wc.txt")

    val rdd2 = rdd1.flatMap(x=>{
      println("-----------------------------------")
      x.split(" ")
    })

    val rdd3 = rdd2.map(_.length)

    //将rdd的数据缓存
    val rdd10 = rdd3.cache()

    val rdd4 = rdd10.map(x=>x*x)

    println(rdd4.collect().toList)

    val rdd5 = rdd10.map(x=>x+x)

    val rdd6 = rdd5.map(x=>x*x)

    println(rdd6.collect().toList)

    Thread.sleep(100000)
  }*/

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    val rdd1 = sc.textFile("datas/wc.txt")
    val rdd2 = rdd1.flatMap(x=>{
      println("--------------------------")
      x.split(" ")
    })
    val rdd3 = rdd2.map((_,1))
    val rdd4 = rdd3.reduceByKey(_+_)
    val rdd5 = rdd4.sortBy(x=>{
      println("++++++++++++++++++++++++++++++")
      x._2
    })

    //val rdd5 = rdd4.keyBy[K](f).sortByKey(ascending, numPartitions).values
    //val rdd6 = rdd4.sortBy(_._2,true)
    //val rdd6 = rdd4.keyBy[K](f).sortByKey(ascending, numPartitions).values.map(x=>(x._1,x._2+10))
    val rdd6 = rdd5.map(x=>(x._1,x._2+10))
    //val rdd7 = rdd4.keyBy[K](f).sortByKey(ascending, numPartitions).values.map(x=>(x._1,x._2+20))
    val rdd7 = rdd5.map(x=>(x._1,x._2+20))

    println(rdd6.collect())

    println(rdd7.collect())

    Thread.sleep(1000000)
  }
}
