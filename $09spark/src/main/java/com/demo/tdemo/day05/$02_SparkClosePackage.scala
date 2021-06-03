package com.demo.tdemo.day05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class $02_SparkClosePackage {

  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

  @Test
  def closePackage(): Unit ={

    val rdd = sc.parallelize(List(1,2,3,4,5,6,7))

    val x = 100

    val rdd2 = rdd.map(y=>{
      y+x
    })

    println(rdd2.collect().toList)
  }


  @Test
  def closePackage2(): Unit ={

    val rdd = sc.parallelize(List(1,2,3,4,5,6,7))

    //val person = new Person

    //val rdd2 = person.add( rdd )
    val student = Student(20)
    val rdd2 = student.add(rdd)
    println(rdd2.collect().toList)
  }
}
//样例类默认就是实现了序列化接口的
case class Student(val age:Int){


  def add(rdd:RDD[Int]):RDD[Int] = {

    rdd.map(x=> x + this.age)
  }
}


class Person {

  val age = 20

  def add(rdd:RDD[Int]):RDD[Int] = {
    val y = this.age
    rdd.map(x=> x + y)
  }

}
