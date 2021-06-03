package com.demo.tdemo.day06

import org.apache.spark.{SparkConf, SparkContext}

object $02_BroadCast {

  /**
    * 广播变量
    *     使用场景：
    *         1、在spark算子函数中有使用Driver 数据的时候，可以使用广播变量减少内存占用
    *         2、大表join小表的时候,可以减少shuffle
    *     使用:
    *         1、将Driver的数据广播到Executor中: val bc = sc.broadcast(数据)
    *         2、task使用executor的数据: bc.value()
    * @param args
    */
  def main2(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val map = Map("jd"->"www.jd.com","ali"->"www.ali.com","tm"->"www.tm.com")

    val bc = sc.broadcast(map)

    val rdd = sc.parallelize(List("jd","ali","tm"))

    //val rdd2 = rdd.map(x=> map.getOrElse(x,""))
    val rdd2 = rdd.map(x=> {
      val value = bc.value
      value.getOrElse(x,"")
    })

    println(rdd2.collect().toList)

    Thread.sleep(1000000)

  }

  //大表join小表的时候,可以减少shuffle
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

    val rdd1 = sc.parallelize(List( ("1001","大数据开发部") ,("1002","市场部") ,("1003","业务部") ))

    val rdd2 = sc.parallelize( List ( ("A001","张三","1001"),("A002","lisi","1001"),("A003","wangwu","1003"),("A004","zhaoliu","1002")))

    //获取每个员工信息信息+部门名称
 /*   val rdd3 = rdd2.map{
      case (id,name,deptid) => (deptid,(id,name))
    }

    val rdd4 = rdd3.join(rdd1)

    println(rdd4.collect().toList)*/

    //收集部门表数据
    val deptList = rdd1.collect().toMap

    val bc = sc.broadcast(deptList)

    val rdd3 = rdd2.map{
      case (id,name,deptid) => (id,name,bc.value.getOrElse(deptid,""))
    }

    println(rdd3.collect().toList)

    Thread.sleep(1000000)
  }
}
