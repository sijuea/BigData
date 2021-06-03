package com.demo.tdemo.day01

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args: Array[String]): Unit = {

    //1、创建SparkContext
    //setMaster一般在打包提交到集群执行的时候需要注释掉
    val conf = new SparkConf().setMaster("local[4]").setAppName("test")
    val sc = new SparkContext(conf)
    //2、读取数据
    val rdd = sc.textFile("datas/wc.txt")
    //3、切割、压平
    val rdd2 = rdd.flatMap(_.split(" ") )

    //[Hello,Spark,Scala]
    //4、分组、聚合
    /*val rdd3 = rdd2.groupBy(x=>x)

    val rdd4 = rdd3.map(x=>(x._1,x._2.size))*/
    val rdd3 = rdd2.map( x=>(x,1) )

    println(rdd3.partitions.length)

    val rdd4 = rdd3.reduceByKey( (agg,curr)=>agg+curr )
    println(rdd4.partitions.length)
    //分组+聚合
    //
    //5、结果展示
    val result = rdd4.collect()
    println(result.toList)

    Thread.sleep(1000000)

  }
}
