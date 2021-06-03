package com.demo.tdemo.day06

import org.apache.spark.{SparkConf, SparkContext}

object $03_Test {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    //1、读取数据
    val rdd1 = sc.textFile("datas/user_visit_action.txt")
    //2、过滤搜索数据

    //3、统计每个品类的点击数
    val clickRdd = rdd1.filter(line=> {
      val arr = line.split("_")
      arr(6)!="-1"
    })

    //3.1、列裁剪[点击的品类id]
    val clieckSelectRdd = clickRdd.map(line=>{
      val arr = line.split("_")
      (arr(6),1)
    })

    //3.2、分组聚合
    val clickNumRdd = clieckSelectRdd.reduceByKey(_+_)

    //4、统计每个品类的下单数
    //4.1 过滤下单数据
    val orderRdd = rdd1.filter(line=>{
      val arr = line.split("_")
      arr(8)!="null"
    })
    //4.2、列裁剪
    val orderSelectRdd = orderRdd.flatMap(line=>{
      val arr = line.split("_")
      arr(8).split(",").map(x=>(x,1))
    })

    val orderNumRdd = orderSelectRdd.reduceByKey(_+_)
    //5、统计每个品类的支付数
    //5.1、过滤支付数据
    val payRdd = rdd1.filter(line=>{
      val arr = line.split("_")
      arr(10)!="null"
    })
    //5.2、列裁剪
    val paySelectRdd = payRdd.flatMap(line=>{
      val arr = line.split("_")
      arr(10).split(",").map(x=>(x,1) )
    })

    //5.3、统计
    val payNumRdd = paySelectRdd.reduceByKey(_+_)
    //6、三者join 得到每个品类的点击数、下单数、支付数
    val totalRdd = clickNumRdd.leftOuterJoin(orderNumRdd).leftOuterJoin(payNumRdd)

    val totalNumRdd = totalRdd.map{
      case (id,((clickNum,orderNum),payNum)) => (id,clickNum,orderNum.getOrElse(0),payNum.getOrElse(0))
    }

    //7、排序取前十
    totalNumRdd.sortBy({
      case (id,clickNum,orderNum,payNum) => (clickNum,orderNum,payNum)
    },false).take(10)
      .foreach(println(_))


  }
}
