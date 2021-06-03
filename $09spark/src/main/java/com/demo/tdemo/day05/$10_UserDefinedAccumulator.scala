package com.demo.tdemo.day05

import org.apache.spark.{SparkConf, SparkContext}

object $10_UserDefinedAccumulator {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("test"))

    val acc = new WordCountAccumulator
    sc.register(acc,"wc")
    val rdd = sc.textFile("datas/wc.txt")

    val rdd2 = rdd.flatMap(_.split(" "))

    val rdd3 = rdd2.map((_,1))

    //val rdd4 = rdd3.reduceByKey(_+_)
    rdd3.mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index} data=${it.toList}")
      it
    }).collect()
    //println(rdd4.collect().toList)
    rdd3.foreach(x=> acc.add(x))

    val result = acc.value
    println(result.toList)
  }
}
