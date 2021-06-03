package com.demo.training.day02

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

/**
 * @author sijue
 * @date 2021/4/26 11:42
 * @describe
 */
class RDDRelevant {

    val conf = new SparkConf().setMaster("local[4]").setAppName("RDDRelve")
    val sc=new SparkContext(conf)

  @Test
  def createRDDFromList()={
    val rdd1 = sc.makeRDD(List(1, 2, 3, 4))
    val list=List(("aa",List("a1","a2","a3")),
                        ("aa",List("b1","b2","b3")),
                          ("cc",List("c1","c2","c3")))
   val rdd2=sc.makeRDD(list)
    println(rdd2.collect().toList)
  }
  @Test
  def createRDDFromList2()={
    val rdd1 = sc.parallelize(List(1, 2, 3, 4))

    rdd1.collect().foreach(println(_))
  }
  @Test
  def createRDDFromFile()={
    val rdd1 = sc.textFile("datas/wc.txt")
    //读取Hdfs文件,需要将代码打成jar包，在hdfs环境运行
    sc.textFile("hdfs://hadoop102:9820/wc.txt")
    sc.textFile("hdfs:///wc.txt")
    sc.textFile("hdfs:///wc.txt")
    rdd1.collect().foreach(println(_))
  }

  @Test
  def getRddFromOther()={
    val rdd1=sc.makeRDD(List("hadop","scala","asdff"))
    val  rdd2=rdd1.map(x=>(x,1))
    rdd2.collect().toList.foreach(println(_))
  }
  /*创建RDD时候指定分区:这个点重点还是不同情况下RDD的分区数的总结点*/
  @Test
  def RDDPartition()={
    val rdd1=sc.parallelize(List("java","scala","flume","asss","xzxxx"))
    println(rdd1.partitions.length)

  }
}
