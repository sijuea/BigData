package com.demo.tdemo.day04

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.{Partitioner, SparkConf, SparkContext}
import org.junit.Test

class $03_Action extends Serializable {

  @transient
  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("action"))

  /**
    * 对RDD里面的所有元素聚合,最终得到一个结果
    */
  @Test
  def reduce(): Unit ={

    val rdd = sc.parallelize(List(10,2,5,3,7,9,8))

    println(rdd.reduce((agg,curr)=>{
      println(s"agg=${agg} curr=${curr}")
      agg+curr
    }))

  }

  //collect: 收集RDD每个分区的数据返回给Driver  **********
  //  collect收集RDD每个分区的数据返回给Driver，如果RDD分区的数据比较多,Driver的内存默认只有1G，所以可能导致Driver内存不够用出现内存溢出。
  //  工作中Driver内存一般会调整为5-10G
  @Test
  def collect() ={
    val rdd = sc.parallelize(List(10,2,5,3,7,9,8))
    val arr = rdd.collect()
    println(arr.toList)
  }

  /**
    * 统计RDD元素个数
    */
  @Test
  def count(): Unit ={
    val rdd = sc.parallelize(List(10,2,5,3,7,9,8))
    println(rdd.count())
  }

  /**
    * 获取RDD第一个元素
    */
  @Test
  def first(): Unit ={

    val rdd = sc.parallelize(List(10,2,5,3,7,9,8))
    rdd.map(x=>(x,1)).partitionBy(new MyPartitioner(4)).first()
    //println(rdd.first())

    //Thread.sleep(100000)
  }

  /**
    * 获取RDD前N个元素
    */
  @Test
  def take(): Unit ={
    val rdd = sc.parallelize(List(10,2,5,3,7,9,8))

    println(rdd.take(3).toList)
  }

  /**
    * 获取RDD排序之后的前N个元素
    */
  @Test
  def takeOrdered():Unit = {
    val rdd = sc.parallelize(List(10,2,5,3,7,9,8))

    println(rdd.takeOrdered(3).toList)
  }

  /**
    * aggregate()
    */
  @Test
  def aggregate():Unit={
    val rdd = sc.parallelize(List(10,2,5,3,7,9,8))
    rdd.aggregate(1000)((agg,curr)=>{
      println(s"分区间聚合: agg=${agg} curr=${curr}")
      agg+curr
    },(agg,curr)=>{
      println(s"Driver聚合: agg=${agg} curr=${curr}")
      agg+curr
    })
  }

  @Test
  def fold(): Unit ={
    val rdd = sc.parallelize(List(10,2,5,3,7,9,8))

    println(rdd.fold(1000)(_ + _))
  }

  /**
    * 统计Key出现了多少次  ***********
    *     countByKey一般用于数据倾斜场景
    *     数据倾斜之后一般先通过sample采样,采样之后通过countByKey统计每个key的次数，可以得知哪个key出现了数据倾斜
    * countByKey会产生shuffle
    */
  @Test
  def countByKey(): Unit ={
    val rdd = sc.parallelize(List( ("aa",1),("bb",2),"aa"->3,"dd"->10,"ff"->20,"bb"->30 ))
    println(rdd.countByKey)


  }

  @Test
  def save(): Unit ={
    val rdd = sc.parallelize(List( ("aa",1),("bb",2),"aa"->3,"dd"->10,"ff"->20,"bb"->30 ))
    rdd.saveAsTextFile("output/text")
  }

  /**
    * foreach(func: RDD元素类型 => B ): Unit
    *     foreach里面的函数是针对每个元素操作
    */
  @Test
  def foreach(): Unit ={
    val rdd = sc.parallelize(List( ("aa",1),("bb",2),"aa"->3,"dd"->10,"ff"->20,"bb"->30 ))

    rdd.foreach(x=>{
      println(x)
    })
  }

  /**
    * foreachPartition(fun: 迭代器[RDD元素类型]=>B): Unit   *************
    *     foreachPartition里面函数是针对一个分区操作
    * 工作中如果需要将数据保存在mysql之类的需要打开外部链接的地方,此时推荐使用foreachPartition
    *       如果是需要根据rdd元素从mysql之类的需要打开外部链接的地方读取数据,此时推荐使用mapPartitions
    */
  @Test
  def foreachPartition(): Unit ={
    val rdd = sc.parallelize(List( ("aa",1),("bb",2),"aa"->3,"dd"->10,"ff"->20,"bb"->30 ))

    rdd.foreachPartition(it=> {
      var connection:Connection = null
      var statement:PreparedStatement = null
      try{
        connection = DriverManager.getConnection("..")
        statement = connection.prepareStatement("insert into xx values(?,?)")
        var i = 0
        it.foreach(x=>{
          statement.setString(1,x._1)
          statement.setInt(2,x._2)
          statement.addBatch()
          if(i%1000==0){
            statement.executeBatch()
            statement.clearBatch()
          }

        })
        //执行最后一个不满1000条的批次
        statement.executeBatch()

      }catch {
        case e:Exception =>
      }finally {
        if(statement!=null)
          statement.close()
        if(connection!=null)
          connection.close()
      }
    })
  }

  class MyPartitioner(num:Int) extends Partitioner{
    override def numPartitions: Int = num

    override def getPartition(key: Any): Int = {
      key match {
        case _ => 1
      }
    }
  }


}

