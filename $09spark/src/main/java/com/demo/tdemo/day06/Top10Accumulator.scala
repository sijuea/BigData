package com.demo.tdemo.day06

import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

class Top10Accumulator extends AccumulatorV2[(String,(Int,Int,Int)),mutable.Map[String,(Int,Int,Int)]]{
  val map = mutable.Map[String,(Int,Int,Int)]()

  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[(String, (Int, Int, Int)), mutable.Map[String, (Int, Int, Int)]] = new Top10Accumulator

  override def reset(): Unit = map.clear()

  /**
    * 累加元素
    * @param v
    */
  override def add(v: (String, (Int, Int, Int))): Unit = {

    if(map.contains(v._1)){
      val agg = map.get(v._1).get
      map.put(v._1, ( agg._1+v._2._1,agg._2+v._2._2, agg._3+v._2._3 ))
    }else{
      map.+=(v)
    }
  }


  override def merge(other: AccumulatorV2[(String, (Int, Int, Int)), mutable.Map[String, (Int, Int, Int)]]): Unit = {
    val list = map.toList ++ other.value.toList

    val groupMap = list.groupBy(_._1)
    //Map(
    //    1 -> List( (1,(10,2,1)), (1,(30,10,5)) ,..)
    // )
    val result = groupMap.map(x=>{
      //x = 1 -> List( (1,(10,2,1)), (1,(30,10,5)) ,..)
      val elementList = x._2.map(_._2)
      // List( (10,2,1), (30,10,5) ,..)
      val res = elementList.reduce((agg,curr)=>(agg._1+curr._1,agg._2+curr._2,agg._3+curr._3))
      (x._1,res)
    })

    map.++=(result)
  }

  override def value: mutable.Map[String, (Int, Int, Int)] = map
}
