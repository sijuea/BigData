package com.demo.tdemo.day05

import org.apache.spark.{SparkConf, SparkContext}

object $07_Checkpoint {

  /**
    * checkpoint：
    *     1、原因:缓存是将数据保存在本地磁盘或者本机内存中,如果服务器宕机，数据消失需要根据rdd依赖关系从头开始计算得到数据
    *     2、checkoint数据持久化的位置: HDFS中
    *     3、如何使用checkpoint？
    *           1、设置数据的保存路径: sc.setCheckpointDir("checkpoint")
    *           2、持久化rdd ：  rdd.checkpoint()
    *           checkpoint会单独触发一次job的执行,在第一个job执行完成之后触发
    *           在工作中checkpoint一般结合缓存使用,能够减少checkpoint job的执行,checkpoint的时候会直接从缓存中获取数据保存到HDFS
    *
    * 缓存与checkpoint的区别:
    *     1、数据存储位置不一样:
    *         缓存是将数据保存在本地磁盘或者是内存中,安全向相对比较低
    *         checkpoint是将数据保存在HDFS中,安全性更高
    *     2、依赖关系是否切除不一样:
    *         缓存是将数据保存在本地磁盘或者是内存中，可能会出现数据丢失,出现数据丢失之后需要根据rdd的依赖关系重新计算,所以缓存会保留RDD的依赖关系
    *         checkpoint是将数据保存在HDFS中,数据不会丢失, rdd的依赖关系会切除
    *
    */
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    //设置数据持久化路径
    sc.setCheckpointDir("checkpoint")

    val rdd1 = sc.textFile("datas/wc.txt")

    val rdd2 = rdd1.flatMap(x=>{
      println("-----------------------------------")
      x.split(" ")
    })

    val rdd3 = rdd2.map(_.length)

    //将rdd的数据缓存
    val rdd10 = rdd3.cache()
    println(rdd3.toDebugString)
    println("-"*100)
    println(rdd10.toDebugString)
    rdd10.checkpoint()

    val rdd4 = rdd10.map(x=>x*x)

    println(rdd4.collect().toList)

    println("-"*100)
    println(rdd3.toDebugString)
    println("-"*100)
    println(rdd10.toDebugString)
    val rdd5 = rdd10.map(x=>x+x)

    val rdd6 = rdd5.map(x=>x*x)

    println(rdd6.collect().toList)
    println(rdd6.collect().toList)

    Thread.sleep(100000)
  }
}
