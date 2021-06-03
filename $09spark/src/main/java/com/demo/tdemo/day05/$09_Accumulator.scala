package com.demo.tdemo.day05

import org.apache.spark.{SparkConf, SparkContext}

object $09_Accumulator {

  /**
    * 累加器: 先累加单个task,然后将task的累加结果发送给Drvier,由Driver汇总所有task的累加结果
    *   累加器的好处: 可以在一定程度上减少shuffle次数
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val acc = sc.longAccumulator("xx")

    val acc2 = sc.collectionAccumulator[Int]("yy")

    val rdd = sc.parallelize(List(10,3,6,5,3))


    rdd.foreach(x=> {
      //累加元素
      acc.add(x)

      acc2.add(x)
    })
    //获取累加器的最终累加结果
    println(acc.value)

    println(acc2.value)

    Thread.sleep(1000000)

  }
}
