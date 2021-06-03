package com.demo.tdemo.day06

import java.text.SimpleDateFormat

import org.apache.spark.{SparkConf, SparkContext}

object $07_Test {

  def main(args: Array[String]): Unit = {

    //分析的页面路径
    val list = List(1,2,3,4,5,6)
    //1-2  2-3  3-4  4-5  5-6
    val slidingList = list.sliding(2).map(window=> (window.head,window.last)).toList
    //排除最后一位
    val initList = list.init

    // 1跳2的次数/1页面的总次数
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    //1、读取数据
    val rdd1 = sc.textFile("$09spark/datas/user_visit_action.txt")

    //2、列裁剪[页面id,时间,session]
    val rdd2 = rdd1.filter(line=> line.split("_")(6)!="-1").map(line=>{

      val arr = line.split("_")
      val page = arr(3)
      val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val time = formatter.parse(arr(4)).getTime
      val session = arr(2)

      (session,page,time)
    })
    //3、获取分母
    //3.1、过滤出1,2,3,4,5页面的数据、以及点击数据
    val rdd3 = rdd2.filter(x=>{
      initList.contains(x._2.toInt)
    })

    //3.2、统计每个页面的总点击次数
    val rdd4 = rdd3.map( x=> (x._2,1) ).reduceByKey(_+_)
    val fm = rdd4.collect().toMap

    //4、获取分子
    //4.1、根据session分组
    val rdd5 = rdd2.groupBy(_._1)
    val rdd6 = rdd5.flatMap{
      case (session, it ) =>
      //[(((1,2),1),((2,3),2))]
      //4.2、对每个session所有数据排序,得到跳转页面
      val slidingList = it.toList.sortBy(_._3).sliding(2)
      val fromToPageList =  slidingList.map( window=>{
         val first = window.head
         val next = window.last
         ((first._2,next._2),1)
       } )
        fromToPageList
    }

    //4.3、过滤出 1-2  2-3  3-4  4-5  5-6 的数据
    val rdd7 = rdd6.filter{
      case (key,value) => slidingList.contains((key._1.toInt,key._2.toInt))
    }

    //4.4、统计1-2  2-3  3-4  4-5  5-6 的次数
    val rdd8 = rdd7.reduceByKey(_+_)
    val fz = rdd8.collect().toMap
    //5、求得转化率
    slidingList.foreach{
      case (fromPage,toPage) =>
        val lv = fz.getOrElse((fromPage.toString,toPage.toString),0).toDouble / fm.getOrElse(fromPage.toString,1)
        println(s"从${fromPage}跳到${toPage}的转化率=${lv*100}%")
    }
  }
}
