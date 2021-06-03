package com.demo.tdemo.day07

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.junit.Test

case class Person(name:String,age:Int,address:String)

class $02_DataFrameCreate {

  val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  /**
    * DataFrame的创建方式:
    *     1、通过toDF方法创建
    *     2、通过api创建
    *     3、通过读取文件创建
    */

  /**
    * 通过toDF方法创建  *******
    *     1、根据集合创建
    *     2、根据rdd创建
    */
  @Test
  def toDFCreateDF(): Unit ={
    //根据集合创建
    val list: List[Person] = List( Person("lisi",20,"shenzhen"),Person("wangwu",30,"beijing"),Person("zhaoliu",25,"shenzhen") )

    val list2 = List( ("lisi",20,"shenzhen"),("wangwu",30,"beijing"),("zhaoliu",25,"shenzhen") )

    import spark.implicits._
    val df: DataFrame = list.toDF
    //集合每一行是元组的时候可以通过有参的toDF方法重定义列名[新的列的个数必须和原来的列的个数一致]
    val df2: DataFrame = list2.toDF("name","age","address")

    df.printSchema()
    df.show
    df2.show

    //根据rdd创建
    val rdd1 = spark.sparkContext.parallelize(list)

    val df3 = rdd1.toDF()

    val rdd2 = spark.sparkContext.parallelize(list2)

    val df4 =rdd2.toDF("name","age","address")
    df3.show
    df4.show
  }

  /**
    * 通过createDataFrame 创建
    */
  @Test
  def createDFByApi(): Unit ={
    val rdd1 = spark.sparkContext.parallelize(List(
      Row("lisi1",201,"shenzhen1","man1"),
      Row("lisi2",202,"shenzhen2","man2"),
      Row("lisi3",203,"shenzhen3","man3")
    ))

    val fields = Array(
      StructField("name",StringType),
      StructField("age",IntegerType),
      StructField("address",StringType),
      StructField("sex",StringType)
    )
    val schema = StructType(fields)

    val df = spark.createDataFrame(rdd1,schema)

    df.show
  }

  @Test
  def readFileCreateDataFrame(): Unit ={

    //spark.read.textFile("")
    spark.read.json("datas/xx.json").show
  }
}
