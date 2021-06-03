package com.demo.training.sparkCore.$05aggregate

import com.demo.tdemo.day05.WordCountAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author sijue
 * @date 2021/4/29 22:58
 * @describe
 */
object TestMyAggera {
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setMaster("local[4]").setAppName("test")
    var sc=new SparkContext(conf)
    val rdd1 = sc.textFile("datas/wc.txt")
    val myacc=new WordCountAccumulator
    sc.register(myacc,"acc")
    val rdd2 = rdd1.flatMap(x => x.split(" "))
    val rdd3 = rdd2.map(x => (x, 1))
    rdd3.foreach(x=>myacc.add(x))
    val map = myacc.value
    println(map.toList)
  }

}
