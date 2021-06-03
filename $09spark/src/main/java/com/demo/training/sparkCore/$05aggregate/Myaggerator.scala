package com.demo.training.sparkCore.$05aggregate

import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

/**
 * @author sijue
 * @date 2021/4/29 21:17
 * @describe
 */
class Myaggerator extends AccumulatorV2[(String,Int),mutable.Map[String,Int]]{
  var map=mutable.Map[String,Int]()
  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[(String, Int), mutable.Map[String, Int]] = new Myaggerator

  override def reset(): Unit = map.clear()

  override def add(v: (String, Int)): Unit = {
    if(map.contains(v._1)){
      val res = map.getOrElse(v._1,0)+v._2
      map.put(v._1,res)
    }else{
      map.+=(v)
    }
  }

  override def merge(other: AccumulatorV2[(String, Int), mutable.Map[String, Int]]): Unit = {
    val allMap = map.toList ++ other.value.toList
    val result = allMap.groupBy(_._1).map(x => (x._1, x._2.map(_._2).sum))
    map++=result
  }

  override def value: mutable.Map[String, Int] = map
}
