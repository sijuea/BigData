package com.demo.tdemo.day07

import org.apache.spark.sql.SparkSession
import org.junit.Test

class $03_DataSetCreate {
  val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  import spark.implicits._

  /**
    * 创建DataSet方式:
    *     1、通过toDS方法
    *     2、通过读取文本文件:
    *     3、通过api
    * 工作中DataSet与DataFrame的使用场景:
    *     1、如果在处理非结构化数据的时候是使用RDD处理,处理完成之后RDD里面的数据类型是样例类,此时随便转成DataFrame与dataSet都行
    *                                                  处理完成之后RDD里面的数据类型是元组,此时推荐使用toDF转成DataFrame,因为使用toDF的时候可以重定义列名
    *     2、如果在sparksql处理数据的时候想要使用map、flatMap这种需要写函数的强类型算子，推荐使用DataSet
    *     其他时候DataSet与DataFrame使用可以随便使用
    */

  /**
    *
    */
  @Test
  def createDSbyApi(): Unit ={
    val list: List[Person] = List( Person("lisi",20,"shenzhen"),Person("wangwu",30,"beijing"),Person("wangwu",30,"beijing"),Person("wangwu",50,"shanghai"),Person("zhaoliu",25,"shenzhen"),Person("hanmeimei",55,"shenzhen"),Person("lilei",35,"beijing") )

    val ds = spark.createDataset(list)

    ds.show
  }

  /**
    * sparksql只有读取文件文件的是才能创建DataSet <常用>
    */
  @Test
  def createDsByReadFile(): Unit ={

    val ds = spark.read.textFile("datas/wc.txt")
    val ds2 = ds.flatMap(line=> line.split(" ")).map((_,1)).toDF("word","num")
    val ds3 = ds2.map(row=>{
      val name = row.getAs[String]("name")
      name
    })
    ds2.createOrReplaceTempView("wc")

    spark.sql("select word,sum(num) num from wc group by word").show
  }

  /**
    * 通过toDS() <常用>
    *     1、通过集合创建
    *     2、通过rdd创建
    */
  @Test
  def createDsByDSMethod(): Unit ={

    //1、通过集合创建
    val list: List[Person] = List( Person("lisi",20,"shenzhen"),Person("wangwu",30,"beijing"),Person("wangwu",30,"beijing"),Person("wangwu",50,"shanghai"),Person("zhaoliu",25,"shenzhen"),Person("hanmeimei",55,"shenzhen"),Person("lilei",35,"beijing") )

    val ds = list.toDS()

    val list2= List( ("lisi",20,"shenzhen"),("wangwu",30,"beijing"),("wangwu",30,"beijing"),("wangwu",50,"shanghai"),("zhaoliu",25,"shenzhen"),("hanmeimei",55,"shenzhen"),("lilei",35,"beijing") )

    val ds2 = list2.toDS()

    //命令式
    ds.filter("age>20").selectExpr("min(age) age").show

    //声明式
    ds.createOrReplaceTempView("person")
    spark.sql("select * from person").show

    ds2.selectExpr("_1 as name","_2 as age","_3 as address").show

    //2、通过rdd创建
    val rdd1 = spark.sparkContext.parallelize(list)
    val ds3 = rdd1.toDS()
    ds3.show
  }
}
