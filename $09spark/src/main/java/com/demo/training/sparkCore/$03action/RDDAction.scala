package com.demo.training.sparkCore.$03action

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

/**
 * @author sijue
 * @date 2021/4/29 18:43
 * @describe
 */
class RDDAction {
 val conf=new SparkConf().setMaster("local[4]").setAppName("test")
  val sc=new SparkContext(conf)

  /**
   * reduce：聚合
   */
  @Test
  def reduce()={
    var rdd1=sc.parallelize(List(1,2,3,5),2)
    println(rdd1.reduce(_ + _))
  }

  /**
   * collect:输出数据
   */
    @Test
  def collect()={
      var rdd1=sc.parallelize(List(1,2,3,5),2)
      println(rdd1.map(x=>(x,"aa")).collect().toList)
  }

  /**
   * first:获取第一个元素：默认是0号分区的第一个元素
   */
    @Test
  def  first()={
    var rdd1=sc.parallelize(List(1,2,3,5),2)
    println(rdd1.first())
  }

  /**
   * take:获取集合前几个元素
   */
    @Test
  def take()={
    var rdd1=sc.parallelize(List(1,2,3,5),2)
    println(rdd1.take(2).toList)
  }

  /**
   * aggregate:聚合数据
   */
  @Test
  def aggregate()={
    var rdd1=sc.parallelize(List(1,2,3,5))
    println(s"xxxxxfenqu${rdd1.partitions.length}")
    val rdd2 = rdd1.aggregate("xx")(_ + _, _ + _)
    println(rdd2)
  }

  /**
   * fold:聚合操作
   */
    @Test
  def  fold()={
    var rdd1=sc.parallelize(List(1,2,3,5))
      println(rdd1.fold(1000)(_ + _))
  }

  /**
   * countByKey
   */
    @Test
  def countByKey()={
    var rdd1=sc.parallelize(List("aa"->1,"bb"->4,"cc"->5,"aa"->4))
    println(rdd1.countByKey())
  }

  /**
   * save
   */
    @Test
  def save()={
      var rdd1=sc.parallelize(List("aa"->1,"bb"->4,"cc"->5,"aa"->4))
      rdd1.saveAsTextFile("datas/1.txt")
  }

  /**
   * foreach:遍历数据输出
   */
    @Test
  def foreach()={
    var rdd1=sc.parallelize(List("aa"->1,"bb"->4,"cc"->5,"aa"->4))
    rdd1.foreach(println(_))
  }

  /**
   * foreachPartition:针对每个分区的数据遍历输出
   */
  @Test
  def foreachPartition()={
    var rdd1=sc.parallelize(List("aa"->1,"bb"->4,"cc"->5,"aa"->4))
    println(rdd1.foreachPartition(x=>println(x.toList)))
  }

}
