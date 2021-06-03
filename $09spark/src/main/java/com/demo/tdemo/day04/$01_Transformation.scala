package com.demo.tdemo.day04

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}
import org.junit.Test

class $01_Transformation extends Serializable {
  //在序列化类的时候会忽略@transient标识的成员
  @transient
  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

  /**
    * partitionBy(分区器):根据指定的分区器重新分区
    */
  @Test
  def partitionBy(): Unit ={

    val rdd1 = sc.parallelize(List( ("zhangsan",20),("lisi",30),"wangwu"->40,"zhaoliu"->15 ))

    println(rdd1.partitions.length)
    val rdd2 = rdd1.partitionBy(new HashPartitioner(5))

    rdd2.mapPartitionsWithIndex((index,it)=>{
      println(s"index=${index} it=${it.toList}")
      it
    }).collect()

    println(rdd2.partitions.length)

    val rdd3 = rdd1.partitionBy(new MyPartitioner(2))
    println(rdd3.partitions.length)
    rdd3.mapPartitionsWithIndex((index,it)=>{
      println(s"index=${index} it=${it.toList}")
      it
    }).collect()
  }

  /**
    * 自定义分区器
    * @param numartitions
    */
  class MyPartitioner(val numartitions:Int) extends Partitioner{
    //获取分区数
    override def numPartitions: Int = if(this.numartitions<4) 4 else this.numartitions
   //根据key获取分区号
    override def getPartition(key: Any): Int = {
      key match{
        case "zhangsan" => 0
        case "lisi" => 1
        case "wangwu" =>2
        case _ => 3
      }
    }
  }

  @Test
  def reduceByKey(): Unit ={

    val rdd = sc.parallelize(List( "java"->2,"spark"->3,"java"->4,"scala"->1,"java"->6,"java"->7,"scala"->10,"scala"->15,"spark"->3 ),3)

    rdd.mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index} it=${it.toList}")
      it
    }).collect()
    val rdd2 = rdd.reduceByKey( (agg,curr)=>{
      println(s"agg=${agg} curr=${curr}")
      agg+curr
    } )

    val rdd3 = rdd2.combineByKey((x:Int)=>x, (agg:Int,curr:Int)=>agg+curr, (agg:Int,curr:Int)=>agg+curr)


    println(rdd2.collect().toList)
    println(rdd3.collect().toList)
  }

  /**
    * groupByKey: 根据key分组
    *     groupByKey生成一个新RDD,新RDD元素类型(分组的key,迭代器[Key对应的所有的value值])
    * groupByKey与reduceByKey的区别:
    *     reduceByKey是分组+聚合。reduceByKey有类似MR的combiner预聚合功能
    *     groupByKey只是分组,没有聚合。groupByKey没有类似MR的combiner预聚合功能
    * reduceByKey的性能要比groupByKey性能高.
    * 工作中一般推荐使用reduceByKey这种高性能shuffle算子
    */
  @Test
  def groupByKey(): Unit ={

    val rdd = sc.parallelize(List( "java"->2,"spark"->3,"java"->4,"scala"->1,"java"->6,"java"->7,"scala"->10,"scala"->15,"spark"->3 ),3)

    val rdd2 = rdd.groupByKey()

    val rdd3 = rdd.groupBy(x=> x._1)

    println(rdd2.collect().toList)
  }

  /**
    * combinerByKey(createCombiner,mergeValue,mergeCombiners)
    *     createCombiner:在combiner的时候对每个组的第一个value值进行转换
    *     mergeValue:  combiner的执行逻辑
    *     mergeCombiners:  reduce的执行逻辑
    */
  @Test
  def combineByKey(): Unit ={

    val rdd = sc.parallelize(List( "语文"->80,"数学"->100,"英语"->75 ,"语文"->90,"数学"->80,"语文"->65,"语文"->85,"数学"->95,"英语"->100,"英语"->80),3)
    //获取每门学科的平均分
    val rdd2 = rdd.map{
      case (name,score) => (name,(score,1))
    }
    val rdd3 = rdd2.reduceByKey((agg,curr)=>(agg._1+curr._1,agg._2+curr._2 ))

    val rdd4 = rdd3.map{
      case (name,(totalscore,totalnum)) => (name,totalscore.toDouble/totalnum)
    }

    println(rdd4.collect().toList)

    rdd.mapPartitionsWithIndex((index,it)=>{
      println(s"index:${index} it=${it.toList}")
      it
    }).collect()

    val rdd5 = rdd.combineByKey( (x:Int)=>(x,1), (agg:(Int,Int),curr:Int) => {
      println(s"combiner agg:${agg}  curr:${curr} ")
      (agg._1+curr,agg._2+1)
    } , ( agg:(Int,Int),curr:(Int,Int) ) => {
      println(s"reduce agg:${agg}  curr:${curr} ")
      ( agg._1+curr._1 ,agg._2+curr._2)
    } )


    val rdd6 = rdd5.map{
      case (name,(totalscore,totalnum)) => (name,totalscore.toDouble/totalnum)
    }

    println(rdd6.collect().toList)
  }
  //foldByKey(zeroValue)(func): 分组聚合
  // zeroValue是在combiner阶段对每个组第一次聚合的时候,func函数第一个参数的初始值
  @Test
  def foldByKey(): Unit ={

    val rdd = sc.parallelize(List( "java"->2,"spark"->3,"java"->4,"scala"->1,"java"->6,"java"->7,"scala"->10,"scala"->15,"spark"->3 ),3)

    val rdd2 = rdd.foldByKey(100)((agg,curr)=>{
      println(s"agg=${agg} cur=${curr}")
      agg+curr
    })

    rdd2.collect()

  }

  /**
    * aggregateByKey(zeroValue:U)(seqOp: (U, V) => U,combOp: (U, U) => U))
    *       seqOp: combiner聚合逻辑
    *       combOp: reduce聚合逻辑
    *
    * reduceByKey[******]、foldByKey、combineByKey、aggregateByKey的区别:
    *     reduceByKey: combiner与reduce的计算逻辑是一样的。combiner在针对每个组第一次计算的时候，combiner函数第一个参数的初始值是使用该组第一个value值
    *     foldByKey: combiner与reduce的计算逻辑是一样的。combiner在针对每个组第一次计算的时候，combiner函数第一个参数的初始值是使用指定的默认值
    *     combineByKey: combiner与reduce的计算逻辑可以不一样。combiner在针对每个组第一次计算的时候，combiner函数第一个参数的初始值是通过第一个函数将第一个value值转换的结果
    *     aggregateByKey: combiner与reduce的计算逻辑可以不一样。combiner在针对每个组第一次计算的时候，combiner函数第一个参数的初始值是使用指定的默认值
    */
  @Test
  def aggregateByKey(): Unit ={
    val rdd = sc.parallelize(List( "语文"->80,"数学"->100,"英语"->75 ,"语文"->90,"数学"->80,"语文"->65,"语文"->85,"数学"->95,"英语"->100,"英语"->80),3)

    val rdd2 = rdd.aggregateByKey( (0,0) )( (agg:(Int,Int),curr: Int)=> ( agg._1+curr,agg._2+1 ) , (agg:(Int,Int),curr:(Int,Int))=> (agg._1+curr._1,agg._2+curr._2) )

    val rdd3 = rdd2.map{
      case (name,(score,num)) => (name,score.toDouble/num)
    }

    println(rdd3.collect().toList)
  }

  /**
    * 根据key排序
    */
  @Test
  def sortByKey(): Unit ={
    val rdd = sc.parallelize(List( "aa"->2,"dd"->3,"cc"->4,"ff"->1,"ee"->6,"zz"->7,"ww"->10,"ss"->15,"ii"->3 ),3)

    val rdd2 = rdd.sortByKey(false)

    val rdd3 = rdd.sortBy( _._1 ,false)
    println(rdd2.collect().toList)
    println(rdd3.collect().toList)
  }

  /**
    * mapValues(func: value的类型 => B): 转换,对value进行转换
    *     mapValues里面函数针对的是KV类型数据的V进行转换
    */
  @Test
  def mapValues(): Unit ={
    val rdd = sc.parallelize(List( "aa"->2,"dd"->3,"cc"->4,"ff"->1,"ee"->6,"zz"->7,"ww"->10,"ss"->15,"ii"->3 ),3)

    val rdd2 = rdd.mapValues(x=>x*x)

    println(rdd2.collect().toList)
  }

  /**
    * join: 两个rdd进行关联,如果key相同则能够关联上
    *
    *   join产生的RDD[(K的类型,(左RDD key对应的value值类型,右RDD key对应的value值类型))]
    */
  @Test
  def join(): Unit ={
    val rdd1 = sc.parallelize(List( "aa"->2,"aa"->100,"dd"->3,"ee"->50 ),3)
    val rdd2 = sc.parallelize(List( "aa"->20,"dd"->30,"ff"->40 ),3)

    val rdd3 = rdd1.join(rdd2)

    println(rdd3.collect().toList)

    val rdd4 = rdd1.leftOuterJoin(rdd2)
    println(rdd4.collect().toList)

    val rdd5: RDD[(String, (Option[Int], Int))] = rdd1.rightOuterJoin(rdd2)
    println(rdd5.collect().toList)

    val rdd6 = rdd1.fullOuterJoin(rdd2)
    println(rdd6.collect().toList)
  }

  /**
    *  cogroup的结果: (key,(左RDD key对应的所有的vlaue值的集合,右RDD key对应的所有的vlaue值的集合))
    */
  @Test
  def cogroup(): Unit = {
    val rdd1 = sc.parallelize(List( "aa"->2,"aa"->100,"dd"->3,"ee"->50 ),3)
    val rdd2 = sc.parallelize(List( "aa"->20,"dd"->30,"ff"->40 ),3)

    val rdd3 = rdd1.cogroup(rdd2)

    println(rdd3.collect().toList)
  }


}


