package com.demo.tdemo.day02

import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

class $01_RDDCreate {

  val conf = new SparkConf().setMaster("local[4]").setAppName("test")
  val sc = new SparkContext(conf)
  /**
    * RDD的创建:
    *     1、根据本地集合创建
    *     2、读取文件创建
    *     3、根据其他RDD衍生
    */

  /**
    * 根据本地集合创建RDD<一般用于测试>
    *     1、val rdd = sc.makeRDD(集合[元素类型])
    *     2、val rdd = sc.parallelize(集合[元素类型])
    */
  @Test
  def createRddByCollection(): Unit = {

    val list = List[String]("hello spark hello scala","spark scala hadoop")
    val rdd = sc.makeRDD(list)
    println(rdd.collect().toList)

    val list2 = List[(String,List[String])](
      ("hello spark",List[String]("aa","bb","cc")),
      ("hadoop flume",List[String]("dd","ee","ff")),
      ("spark scala",List[String]("hh","jj","kk"))
    )

    val rdd2 = sc.makeRDD(list2)
    println(rdd2.collect().toList)

    val rdd3 = sc.parallelize(list)
    println(rdd3.collect().toList)
  }

  /**
    * 根据读取文件创建RDD
    *       1、在集群中执行
    *           1、有配置HADOOP_CONF_DIR[默认读取就是HDFS文件]<工作中一般有配置>:
    *                 1、读取HDFS文件:
    *                      1、sc.textFile("/xx/xxx")
    *                      2、sc.textFile("hdfs:///xx/xx")
    *                      3、sc.textFile("hdfs://namnode_ip:端口/xx/xxx")
    *                 2、读取本地文件: sc.textFile("file:///xx/xxx")
    *           2、没有配置HADOOP_CONF_DIR[默认读取就是本地文件]:
    *                1、读取HDFS文件: sc.textFile("hdfs://namnode_ip:端口/xx/xxx")
    *                2、读取本地文件:
    *                     1、sc.textFile("file:///xx/xxx")
    *                     2、sc.textFile("/xx/xxx")
    *      2、在idea中执行[默认就是读取本地文件]
    *           1、读取HDFS文件: sc.textFile("hdfs://namnode_ip:端口/xx/xxx")
    *           2、读取本地文件: sc.textFile("/xx/xx")
    *
    */
  @Test
  def createRDDByFile() = {

    val rdd = sc.textFile("datas/wc.txt")
    println(rdd.collect().toList)

    val rdd2 = sc.textFile("/wc.txt")
    println(rdd2.collect().toList)
  }

  /**
    * 根据其他RDD衍生
    */
  @Test
  def createRddByRdd(): Unit = {
    val rdd1 = sc.textFile("datas/wc.txt")

    val rdd2 = rdd1.flatMap(_.split(" "))

    println(rdd2.collect().toList)
  }
}
