package com.demo.training.sparkCore.$04Serializer

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

/**
 * @author sijue
 * @date 2021/4/29 21:05
 * @describe
 */
class RDDCacheAndDependency {

  val conf = new SparkConf().
    setMaster("local[4]").setAppName("test")
  val sc = new SparkContext(conf)
  sc.setCheckpointDir("check/")
  @Test
  def rddOther()={
    val rdd1 = sc.textFile("datas/wc.txt")
    println(rdd1.dependencies)
  }
  @Test
  def rddCache(): Unit ={
    val rdd1 = sc.textFile("datas/wc.txt")
    //第一中
//    rdd1.cache()
//    rdd1.persist()
        rdd1.checkpoint()
    rdd1.collect()

  }
}
