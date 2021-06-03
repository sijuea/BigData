package com.demo.tdemo.day05

import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable
//IN: 代表累加器累加的元素类型
//OUT: 代表累加器最终结果值类型
class WordCountAccumulator extends AccumulatorV2[(String,Int),mutable.Map[String,Int]]{
  //累加器容器
  val map = mutable.Map[String,Int]()
  //判断累加器是否为空
  override def isZero: Boolean = map.isEmpty
  //拷贝一个自定义累计器
  override def copy(): AccumulatorV2[(String, Int), mutable.Map[String, Int]] = new WordCountAccumulator
  //重置累加器
  override def reset(): Unit = map.clear()
  //累加元素[在每个task中累加元素]
  override def add(v: (String, Int)): Unit = {
    println(s"add: ${Thread.currentThread().getName}  传入参数=${v} 累加结果:${map}")
    if(map.contains(v._1)){

      val num = map.getOrElse(v._1,0) + v._2

      map.put(v._1,num)
    }else{
      map.+=( v )
    }

  }

  override def value: mutable.Map[String, Int] = map

  //汇总所有task的累加结果[Driver中执行的]
  override def merge(other: AccumulatorV2[(String, Int), mutable.Map[String, Int]]): Unit = {
    println(s"merge: ${Thread.currentThread().getName} 传入单个task的累加结果=${other.value}  累加结果:${map}")
    //获取当前传入的task的统计结果
    val taskMap = other.value

    //合并当前集合与传入的task的结果
    val list = map.toList ++ taskMap.toList

    val grouped = list.groupBy(_._1)

    val result = grouped.map(x => (x._1, x._2.map(_._2).sum))

    map ++= (result)


  }
}
