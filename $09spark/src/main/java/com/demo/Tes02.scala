package com.demo

import org.apache.spark.sql.expressions.{Aggregator, MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, StructType}
import org.apache.spark.sql.{Encoder, Row, SparkSession}

import scala.beans.BeanProperty

/**
 * @author sijue
 * @date 2021/4/28 11:20
 * @describe
 */

object Tes02 {

  case class Person(name: String, add: String, age: Int) {
    @BeanProperty
    var name1: String = _
    @BeanProperty
    var add1: String = _
    @BeanProperty
    var age1: Int = _
  }

  val spark =
    SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  def main(args: Array[String]): Unit = {
    //方式一：常用
    SparkSession.builder().enableHiveSupport()
    //方式二：
    val spark1 = new SparkSession.Builder().master("local[4]").appName("test").getOrCreate()
  }

  class tdtd extends UserDefinedAggregateFunction {
    //聚合函数输入的参数类型
    override def inputSchema: StructType = ???

    //聚合过程中的中间变量类型
    override def bufferSchema: StructType = ???

    //聚合函数最后结果类型
    override def dataType: DataType = ???

    //一致性【这是啥意思，之后补上】
    override def deterministic: Boolean = true

    //初始化：给中间变量赋予初始值
    override def initialize(buffer: MutableAggregationBuffer): Unit = ???

    //在每个分区中对输入的元素进行计算【cobiner函数】
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = ???

    //针对每个分区的对应的结果再次进行聚合
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = ???

    //求得最后结果
    override def evaluate(buffer: Row): Any = ???
  }

  class xxx extends Aggregator[Int, String, Int] {
    //给中间变量赋予初始值
    override def zero: String = ???

    //在每个分区中进行计算
    override def reduce(b: String, a: Int): String = ???

    //对每个分区的结果进行汇总计算
    override def merge(b1: String, b2: String): String = ???

    //计算得到最后结果
    override def finish(reduction: String): Int = ???

    //指定中间变量的编码类型
    override def bufferEncoder: Encoder[String] = ???

    //指定最终结果的编码类型
    override def outputEncoder: Encoder[Int] = ???
  }

  def datatoFDF() = {
    //    val list=List(Person("li","aa",1),Person("bb","b",2),Person("cc","c",34))
    //
    //    spark.udf.register("xx".udaf(new ))
    //    import spark.implicits._;
    //    val df = list.toDF()
    //    df.createOrReplaceGlobalTempView()
    //
    //    df.createGlobalTempView()
  }
}
