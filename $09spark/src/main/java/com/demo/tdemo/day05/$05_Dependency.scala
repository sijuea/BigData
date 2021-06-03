package com.demo.tdemo.day05

import org.apache.spark.{SparkConf, SparkContext}

object $05_Dependency {
  /**
    * rdd可以通过dependencies查看依赖关系
    *    rdd依赖关系分为两大类: 宽依赖、窄依赖
    *          宽依赖: 有shuffle的称为宽依赖【父RDD一个分区的数据被子RDD的多个分区所使用】
    *          窄依赖: 没有shuffle的称为窄依赖【父RDD一个分区的数据只被子RDD的一个分区所使用】
    *Application: 一个SparkContext称之为一个Application
    *    job: 一般是一个action算子产生一个job
    *         stage: 阶段，根据宽依赖划分stage,stage个数 = shuffle个数+1
    *             task: 执行的任务,task的个数 = stage最后一个rdd的分区数
    *DAG: 有向无环图.DAG表示的就是数据的处理流程
    * 一个Application对应多个job
    * 一个job对应多个stage
    * 一个stage对应多个task
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val rdd1 = sc.textFile("datas/wc.txt")
    println(rdd1.dependencies)
    println("-"*100)
    val rdd2 = rdd1.flatMap(_.split(" "))
    println(rdd2.dependencies)
    println("-"*100)
    val rdd3 = rdd2.map((_,1))
    println(rdd3.dependencies)
    println("-"*100)
    val rdd4 =rdd3.reduceByKey(_+_)
    println(rdd4.dependencies)
    println("-"*100)
    println(rdd4.collect().toList)
    Thread.sleep(100000)


  }
}
