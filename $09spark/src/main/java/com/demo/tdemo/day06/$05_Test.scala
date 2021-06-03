package com.demo.tdemo.day06

import org.apache.spark.{SparkConf, SparkContext}

object $05_Test {

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val acc = new Top10Accumulator
    sc.register(acc,"acc")
    //1、读取数据
    val rdd1 = sc.textFile("datas/user_visit_action.txt")

    //2、过滤[过滤搜索数据]
    val rdd2 = rdd1.filter(line=>{
      val arr = line.split("_")
      arr(5)=="null"
    })
    //3、切割转换数据[ (订单,(点击次数,下单次数，支付次数))]
    val rdd3 = rdd2.flatMap(line=>{
      val arr = line.split("_")
      val clickid = arr(6)
      val orderids = arr(8)
      val payids = arr(10)
      //点击行为
      if(clickid!="-1"){
        (clickid,(1,0,0)) :: Nil
        //下单数据
      }else if(orderids!="null"){
        orderids.split(",").map(x=> (x,(0,1,0)))
        //支付行为
      }else{
        payids.split(",").map(x=>(x,(0,0,1)))
      }
    })

    rdd3.foreach(x=>{
      acc.add(x)
    })

    val result = acc.value

    //排序取前十
    result.toList.sortBy(_._2).reverse.take(10)
      .foreach(println(_))


  }
}
