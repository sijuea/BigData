package com.demo.homework

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author sijue
 * @date 2021/5/18 21:22
 * @describe
 */
object SparkTest05 {
  //查询top10的热点商品
  // 【按照每个品类的点击、下单、支付的量来统计热门品类。】
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))
    val rdd1 = sc.textFile("datas/user_visit_action.txt");
    //过滤出【点击】的数据和对应的品类id
    val rdd2 = rdd1.map(line => {
      val arr = line.split("_")
      if (arr(6) != "-1") {
        (arr(6), 1)
      } else {
        (arr(6), 0)
      }
    })
    //分组聚合
    val clickData = rdd2.reduceByKey((agg, cur) => agg + cur)
    //过滤出下单的数据以及对应的品类id
    val rdd3 = rdd1.flatMap(line => {
      val arr = line.split("_")
      if (arr(8) != null) {
        val strs = arr(8).split(",")
        strs.map(x => (x, 1))
      } else {
        Array(("", 1))
      }
    })
    //分组聚合下单的数据
    val orderData = rdd3.reduceByKey((agg, cur) => agg + cur)
    //获取支付的品类id
    val rdd4 = rdd1.flatMap(line => {
      val arr = line.split("_")
      if (arr(10) != null) {
        val strs = arr(10).split(",")
        strs.map(x => (x, 1))
      } else {
        Array(("", 1))
      }
    })
    //分组聚合
    val payData = rdd4.reduceByKey(_ + _)
    //三种类型数据left join
    val value = clickData.leftOuterJoin(orderData).leftOuterJoin(payData)
    val value1 = value.map {
      case (id, ((clickNum, orderNum), payNum))
      => (id, clickNum, orderNum.getOrElse(0), payNum.getOrElse(0))
    }
    //排序取前10
    value1.sortBy({
      case (id, clickNum, orderNum, payNum) => (clickNum, orderNum, payNum)
    }, false).take(10).foreach(println(_))
  }
}
