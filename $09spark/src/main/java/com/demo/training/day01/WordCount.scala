package com.demo.training.day01

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author sijue
 * @date 2021/4/21 16:26
 * @describe
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    //datas/wc.txt
    //.setMaster("local[*]") 这个代码在向集群提交jar包的时候需要注掉
    //如果没有注掉，会报错：User did not initialize spark context!
    val conf = new SparkConf().setAppName("test")
    val sc = new SparkContext(conf)
    val value = sc.textFile(args(0)).flatMap(_.split(" "))
    val rdd1 = value.map(x => (x, 1)).reduceByKey((agg, cur) => (agg + cur))
    rdd1.collect().foreach(println(_))
    //      .groupBy(x=>x).map(x=>(x._1,x._2.size))
  }

}
