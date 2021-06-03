package com.demo.tdemo.day05

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class $08_FileReadWrite {

  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

  @Test
  def read(): Unit ={
    //读取文本文件
    sc.textFile("datas/wc.txt").foreach(println(_))
    //采用hadoop api读取数据
    // mapred.xxx
    //sc.hadoopFile()
    // mapreduce.xx
    //sc.newAPIHadoopFile()

    //读取对象文件
    sc.objectFile[Int]("output/obj").foreach(println(_))

    //读取序列化文件
    sc.sequenceFile[String,Int]("output/seq").foreach(println(_))

  }

  @Test
  def write(): Unit ={
    val rdd = sc.parallelize(List(1,3,5,6,7,8))

    rdd.saveAsObjectFile("output/obj")

    val rdd2 = sc.parallelize(List("aa"->1,"bb"->2))
    rdd2.saveAsSequenceFile("output/seq")

  }
}
