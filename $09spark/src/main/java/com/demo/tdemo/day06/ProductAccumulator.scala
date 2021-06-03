package com.demo.tdemo.day06

import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

class ProductAccumulator extends AccumulatorV2[(String,String,Double),mutable.Map[String,(String,Double)]]{

  val map = mutable.Map[String,(String,Double)]()

  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[(String, String, Double), mutable.Map[String, (String, Double)]] = new ProductAccumulator

  override def reset(): Unit = map.clear()

  override def add(v: (String, String, Double)): Unit = {

    //判断省份是否存在,如果存在需要比较当前省份的菜的价格与之前的省份的菜的价格
    if(map.contains(v._1)){

      val value = map.get(v._1).get

      if(value._2 < v._3){
        map.put(v._1,(v._2,v._3))
      }
    }else{
      //如果不存在，直接存储
      map.put(v._1,(v._2,v._3))
    }
  }

  override def merge(other: AccumulatorV2[(String, String, Double), mutable.Map[String, (String, Double)]]): Unit = {

    val list = map.toList ++ other.value.toList

    val groupedMap = list.groupBy{
      case (province,(name,price)) =>province
    }

    val result = groupedMap.map{
      case (province,list) => {
        val max = list.maxBy(_._2._2)
        max
      }
    }

    map.++=(result)

  }

  override def value: mutable.Map[String, (String, Double)] = map

}
