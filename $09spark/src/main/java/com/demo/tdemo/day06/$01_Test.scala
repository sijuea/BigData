package com.demo.tdemo.day06

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Try

object $01_Test {

  //统计每个省份价格最高的农产品
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val acc = new ProductAccumulator

    sc.register(acc,"xx")
    //1、读取数据
    val rdd1 = sc.textFile("datas/product.txt")
    //2、是否过滤、是否去重、是否列裁剪
    val rdd2 = rdd1.filter(line=> line.split("\t").length==6)

    val rdd3 = rdd2.map(line=>{
      val arr = line.split("\t")

      (arr(4) , arr(0) , Try(arr(1).toDouble).getOrElse(0.0) )
    })

    //3、使用累加器统计
    rdd3.foreach(x=>acc.add(x))

    //4、结果
    acc.value.foreach(println(_))
  }
}
