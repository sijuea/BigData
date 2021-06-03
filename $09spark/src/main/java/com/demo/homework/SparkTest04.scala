package com.demo.homework

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.util.Try
/**
 * @author sijue
 * @date 2021/4/29 23:31
 * @describe
 */
object SparkTest04 {
  //先是每个分区计算，然后合并每个分区的结果
  //统计每个省份价格最高的农产品
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setMaster("local[4]").setAppName("test")
    val sc=new SparkContext(conf)
    val acc=new otherAgg
    sc.register(acc,"acc")
    val rdd1 = sc.textFile("datas/product.txt")
    val rdd2 = rdd1.filter(x => x.split("\t").length == 6)
    val rdd3 = rdd2.map(x => {
      val line = x.split("\t")
      (line(4), line(0), Try(line(1).toDouble).getOrElse(0.0))
    })
    rdd3.foreach(x=>acc.add(x))
    println(acc.value.toList)
  }
}
class otherAgg extends AccumulatorV2[(String,String,Double),mutable.Map[String,(String,Double)]]{
  val map=mutable.Map[String,(String,Double)]()

  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[(String, String, Double), mutable.Map[String, (String, Double)]] = new otherAgg

  override def reset(): Unit = map.clear()


  override def add(v: (String, String, Double)): Unit = {
    if(map.contains(v._1)){
      val mapSub = map.get(v._1)
      if(mapSub.get._2<v._3){
          map.put(v._1,(v._2,v._3))
      }
    }else{
      map.put(v._1,(v._2,v._3))
    }
  }

  override def merge(other: AccumulatorV2[(String, String, Double), mutable.Map[String, (String, Double)]]): Unit = {
    val huiz = map.toList ++ other.value.toList
    val grouped = huiz.groupBy {
      case (procince, (cai, price)) => procince
    }
    val units = grouped.map {
      case (p, list) =>  {
          val tuple = list.maxBy(_._2._2)
          tuple
        }


    }
    map.++=(units)

  }

  override def value: mutable.Map[String, (String, Double)] = map
}
