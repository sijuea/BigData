package com.demo.tdemo.day06

import org.apache.spark.{SparkConf, SparkContext}

object $06_Test {

  //Top10热门品类 中每个品类的Top10活跃Session[只统计点击]统计
  def main(args: Array[String]): Unit = {

    //1、得到top10热门品类
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

    val accValue = acc.value

    //排序取前十
    val top10List = accValue.toList.sortBy(_._2).reverse.take(10).map(_._1)
    //广播数据
    val bc = sc.broadcast(top10List)
    //2、根据top10热门品类id过滤数据、过滤点击数据
    val datas = rdd1.filter(line=>{
      val arr = line.split("_")
      //过滤非点击数据以及非热门品类数据
      arr(6)!="-1" && bc.value.contains(arr(6))
    })
    //3、列裁剪[热门品类id、session]
    val selectRdd = datas.map(line=>{
      val arr = line.split("_")
      ( (arr(6),arr(2)),1)
    })
    //4、分组统计
    //统计每个品类每个session的次数
    val result = selectRdd.reduceByKey(_+_)

    //按照品类分组
    val groupedRdd = result.groupBy(_._1._1)

    val res = groupedRdd.map(x=>{
      val top10Session = x._2.toList.sortBy(_._2).reverse.take(10).map(y=>(y._1._2,y._2))
      (x._1,top10Session)
    })
    //5、结果
    res.collect().foreach(println(_))
  }
}
