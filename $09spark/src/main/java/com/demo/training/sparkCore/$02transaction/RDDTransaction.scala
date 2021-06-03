package com.demo.training.sparkCore.$02transaction

import com.esotericsoftware.kryo.Kryo
import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}
import org.apache.spark.{HashPartitioner, Partition, SparkConf, SparkContext}
import org.junit.Test

/**
 * @author sijue
 * @date 2021/4/26 20:32
 * @describe
 */
class RDDTransaction {
   val conf: SparkConf = new SparkConf().setMaster("local[4]").setAppName("day03")
   val sc = new SparkContext(conf)

  /**
   * 映射
   */
  @Test
  def map()={
    var rdd1=sc.parallelize(List("java","scala","fluem","spark","aaswqqw"))
    var rdd2=rdd1.map(x=>(x,1))
    rdd2.collect().foreach(x=>print(x+"\t"))
  }



  /**
   * 一次操作一个分区的数据
   */
  @Test
  def mapPartition()={
    var rdd1=sc.parallelize(
      List("java","scala","fluem","spark","aaswqqw","lsi","ws","xwedkwod","xzx"))
    val result = rdd1.mapPartitions(it => {
      it.map(x => (x, x.length))
//      it.filter(x => x.contains("u"))
    }).collect
    result.foreach(println(_))
  }
  /**
   * mapPartitionsWithIndex
   * 根据分区索引操作分区的数据，可以对不同分区的数据进行不同的处理
   */
  @Test
  def mapPartitionsWithIndex()={
    var rdd1=sc.parallelize(
      List("java","scala","fluem","spark","aaswqqw","lsi","ws","xwedkwod","xzx"))
    rdd1.mapPartitionsWithIndex({
      case (index,it)=>it.filter(x=>x.contains("a"))

    }).collect().foreach(x=>print(x+" "))
  }

  /**
   * flatMap=map+flattern
   *消除第二层list
   */
  @Test
  def flatMap()={

    var rdd1=sc.parallelize(
      List("java scala","fluem spark","aaswqqw lsi","ws xwedkwod","xzx"))
    val value = rdd1.flatMap(x => x.split(" "))
    value.collect().foreach(x=>print(x+" "))
  }

  /**
   * groupBy：分组
   */
  @Test
  def groupBy()={
    var rdd1=sc.parallelize(
      List("java scala","fluem spark","aaswqqw lsi","ws xwedkwod","xzx"))
    val value = rdd1.groupBy(x => x.split(" "))
    value.collect().foreach(x=>print(x+" "))
  }

  /**
   * filter
   *过滤
   */
    @Test
  def filter(): Unit ={
    var rdd1=sc.parallelize(
      List("java","scala","fluem","spark","aaswqqw","lsi","ws","xwedkwod","xzx"))
    rdd1.filter(x=>x.contains("sc")).foreach(x=>print(x+" "))
  }

  /**
   * simple
   * 采样
   */
  @Test
  def sample()={
    var rdd1=sc.parallelize(List(1,2,3,5,5,3,21,4))
    //true表示拿出来放回去，5表示，每个数字最少拿5次
//    rdd1.sample(true,5).foreach(x=>print(x+" "))
    println("*"*100)
    //false表示拿出来不放回去，0.5表示，每个数字被拿到的概率是0.5
    rdd1.sample(false,0.5).foreach(x=>print(x+" "))
  }


  /**
   * distinc
   * 去重
   */
    @Test
  def distinct()={
      var rdd1=sc.parallelize(List(1,2,3,5,5,3,21,4))
      println(rdd1.distinct().collect().toList)
  }

  /**
   * coalesce
   * 减少分区
   */
    @Test
  def coalesce()={
    var rdd1=sc.parallelize(List(1,2,3,5,5,3,21,4,4,5,7,4,2,1))
    println("before="+rdd1.partitions.length)
      val rdd2 = rdd1.coalesce(2,true)
      rdd2.collect()
      Thread.sleep(10000000)
    println("after="+rdd2.partitions.length)

    }

  /**
   * repartition
   * 增加分区
   */
    @Test
  def repartition()={
    var rdd1=sc.parallelize(List(1,2,3,5,5,3,21,4,4,5,7,4,2,1),2)
      //因为repartition不管有没有增加分区都会有shuffle，所以当前job有两个stage
    val rdd2=rdd1.repartition(4)
      println(rdd2.partitions.length)
      rdd2.collect()
    Thread.sleep(10000000)
  }

  /**
   * sortBy 根据指定字段排序
   * 有shuffle
   */
  @Test
  def sortBy()={
    var rdd1=sc.parallelize(
      List("java","scala","fluem","spark","aaswqqw","lsi","ws","xwedkwod","xzx"))
    //默认是升序,false是降序
    val value = rdd1.sortBy(x => x,false)
    value.collect().foreach(println(_))
  }

  /**
   * pipe：调用外部脚本执行命令
   */
  def pipe()={
   //在spark-shell中实现

  }
  /*双 value------------------------------------------------------*/
  /**
   * intersection:交集
   */
  @Test
  def intersection()={
    var rdd1=sc.parallelize(List(1,2,3,5),2)
    var rdd2=sc.parallelize(List(1,2,3),4)
    println(rdd1.intersection(rdd2).collect().toList)
    Thread.sleep(100000)
  }

  /**
   * subtract:差集：rdd1中有，rdd2中没有
   */
    @Test
  def subtract()={
    var rdd1=sc.parallelize(List(1,2,3,5))
    var rdd2=sc.parallelize(List(1,2))
    rdd1.subtract(rdd2).foreach(println(_))
  }

  /**
   * union ：并集
   * 没有shuffle
   */
  @Test
  def union()={
    var rdd1=sc.parallelize(List(1,2,3,5))
    var rdd2=sc.parallelize(List(1,2),2)
    rdd1.union(rdd2).foreach(println(_))
  }

  /**
   * zip:拉链
   * 要求两个rdd的元素和分区数完全一致,没有shuffle
   */
  @Test
  def zip()={
    var rdd1=sc.parallelize(List(1,2,3,5),2)
    var rdd2=sc.parallelize(List(1,2,4,6),2)
    rdd1.zip(rdd2).foreach(println(_))
  }

  /*key-value--------------------------------------------------*/
  /**
   * partitionBy
   * @return
   */
  @Test
  def partitionBy():Unit={
    val rdd1=sc.parallelize(List("hello"->3,"java"->1,"flume"->1,"scala"->3))
    //使用hash分区
    val rdd2 = rdd1.partitionBy(new HashPartitioner(2))
    val rdd3 = rdd2.mapPartitionsWithIndex((index, it) => {
      println(s"分区:${index},数据：${it.toList}")
      it
    }).collect

    val rdd4=rdd1.partitionBy(new MyPartition(3))
    val rdd5 = rdd4.mapPartitionsWithIndex((index, it) => {
      println(s"rdd4-分区:${index},rdd4-数据：${it.toList}")
      it
    }).collect
  }

  /**
   * reduceBykey :分组聚合
   */
    @Test
  def reduceByKey():Unit={
    val rdd1=sc.parallelize(
      List("hello"->3,"java"->1,"flume"->1,"hello"->2,"scala"->3,"flink"->5))

      val value = rdd1.reduceByKey(_ + _)
//      value.mapPartitionsWithIndex((i,it)=>{
//        println(s"分区：${i},数据：${it.toList}")
//        it
//      }).collect()
      value.foreach(println(_))
  }

  /**
   * groupByKey :根据key分组
   *
   */
  @Test
  def groupByKey():Unit={
    val rdd1=sc.parallelize(
      List("hello"->3,"java"->1,"flume"->1,"hello"->2,"scala"->3,"flink"->5))
    val value = rdd1.groupBy(_._1)
    value.foreach(x=>{x._2.toList})
    value.foreach(println(_))
  }

  /**
   * combineBykey
   */
@Test
  def  combineByKey()={
  val rdd = sc.parallelize(
    List( "语文"->80,"数学"->100,"英语"->75 ,"语文"->90,"数学"->80,
      "语文"->65,"语文"->85,"数学"->95,"英语"->100,"英语"->80),3)
    //获取每个学科的平均成绩:reduceBykey的方式
  val rdd2 = rdd.map {
    case (subject, score) => (subject, (score.toInt, 1))
  }
  val rdd3 =
    rdd2.reduceByKey((agg, cur) => (agg._1 + cur._1, agg._2+cur._2))
  rdd3.map(x=>(x._1,x._2._1.toDouble/x._2._2)).foreach(println(_))
  println("*"*100)
  //获取每个学科的平均成绩:combineBykey的方式
  val rdd4=rdd.combineByKey(x=>(x,1),
    (agg:(Int,Int),cur:Int)=>(agg._1+cur,agg._2+1),
    (agg:(Int,Int),cur:(Int,Int))=>(agg._1+cur._1,agg._2+cur._2)
  ).map(x=>(x._1,x._2._1.toDouble/x._2._2)).foreach(println(_))
}

  /**
   * foldByKey:分组聚合
   * zoreValue:默认值，只有在combiner预聚合的时候会加上，在聚合的时候不会加
   */
  @Test
  def foldByKey()={
    val rdd1=sc.parallelize(
      List("hello"->3,"java"->1,"flume"->1,"hello"->2,"scala"->3,"flink"->5))
    rdd1.foldByKey(100)((agg,cur)=>agg+cur).foreach(println(_))
  }


  /**
   *aggregateByKey :分组聚合：（默认值，也有初始值映射）
   */
    @Test
  def aggregateByKey()={
    val rdd = sc.parallelize(
      List( "语文"->80,"数学"->100,"英语"->75 ,"语文"->90,"数学"->80,
        "语文"->65,"语文"->85,"数学"->95,"英语"->100,"英语"->80),3)
    //求学科的平均成绩
    rdd.aggregateByKey((0,0))(
      (agg:(Int,Int),cur:Int)=>(agg._1+cur,agg._2+1),
        (agg:(Int,Int),cur:(Int,Int))=>(agg._1+cur._1,agg._2+cur._2)).foreach(println(_))
  }

  /**
   * sortByKey :根据key排序
   */
  @Test
  def sortByKey():Unit={
    val rdd1=sc.parallelize(
      List("cc"->3,"dd"->1,"aa"->1,"ab"->2,"ba"->3,"ca"->5))
    rdd1.mapPartitionsWithIndex((i,t)=>{
      println(s"${i},${t.toList}")
      t
    }).collect()
//    println(rdd1.sortByKey().collect().toList)
  }

  /**
   * mapValues:对每个key对应的value值操作
   */
    @Test
  def mapValues()={
    val rdd1=sc.parallelize(
      List("cc"->3,"dd"->1,"aa"->1,"ab"->2,"ba"->3,"ca"->5))
    println(rdd1.mapValues(x => x + 10000).collect().toList)

  }

  /**
   * join
   */
    @Test
  def join()={
    var rdd1=sc.parallelize(
      List("za"->4,"ls"->2,"ww"->3,"ww"->2,"zl"->4),3)
    var rdd2=sc.parallelize(
      List("za"->3,"ls"->1,"ww"->1,"zls"->4),3)
    //rdd1和rdd2的交集
    val rdd3 = rdd1.join(rdd2)
//    println(rdd3.collect().toList)
      //rdd1和rdd2的左外连接
      val rdd4 = rdd1.leftOuterJoin(rdd2)
//      println(rdd4.collect().toList)
      // rdd1和rdd2的右外连接
      val rdd5 = rdd1.rightOuterJoin(rdd2)
//      println(rdd5.collect().toList)
      val rdd6=rdd1.fullOuterJoin(rdd2)
      println(rdd6.collect().toList)

  }

  /**
   * cogroup
   */
  @Test
  def cogroup={
    val rdd1 = sc.parallelize(List( "aa"->2,"aa"->100,"dd"->3,"ee"->50 ),3)
    val rdd2 = sc.parallelize(List( "aa"->20,"dd"->30,"ff"->40 ),3)
    println(rdd1.cogroup(rdd2).collect().toList)

  }
}
