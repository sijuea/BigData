package com.demo.tdemo.day04

import org.apache.spark.{SparkConf, SparkContext}

object $02_Test {

  //获取每个省份点击量前三的广告
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    //1、读取数据
    val rdd = sc.textFile("datas/agent.log")
    //2、是否要过滤、是否要去重、是否要列裁剪
    val rdd2 = rdd.map(line=>{
      val arr = line.split(" ")
      ( (arr(1) , arr.last) ,1 )
    })
    //3、按照省份+广告,统计每个省份、每个广告点击了多少次
    val rdd3 = rdd2.reduceByKey(_+_)
    //4、按照省份分组,获取前三
    val rdd4 = rdd3.groupBy{
      case ( (province,ad),num ) => province
    }

    val rdd5 = rdd4.map{
      case (province,it) =>
        val top3 = it.toList.sortBy(_._2).reverse.take(3).map{
          case ( (provin,ad),num ) => (ad,num)
        }

        (province,top3 )
    }
    //5、结果展示
    rdd5.collect().toList.foreach(println(_))
  }
}
