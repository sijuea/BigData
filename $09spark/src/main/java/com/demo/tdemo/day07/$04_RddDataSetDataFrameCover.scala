package com.demo.tdemo.day07

import org.apache.spark.sql.SparkSession

object $04_RddDataSetDataFrameCover {

  /**
    * dataFrame与dataSet的区别:
    *     dataFrame是弱类型,只能做到运行期安全，编译器不安全
    *        dataset是强类型,编译器与运行期都安全
    *  Row类型代表DataFrame的一行数据
    *  Row取值: row.getAs[列的类型](列名)
    *
    */
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

    import spark.implicits._
    val list: List[Person] = List( Person("lisi",20,"shenzhen"),Person("wangwu",30,"beijing"),Person("wangwu",30,"beijing"),Person("wangwu",50,"shanghai"),Person("zhaoliu",25,"shenzhen"),Person("hanmeimei",55,"shenzhen"),Person("lilei",35,"beijing") )

    //rdd转DataFrame: toDF方法
    val rdd = spark.sparkContext.parallelize(list)
    val df = rdd.toDF
    df.map(row=>{
      val name = row.getAs[String]("name1")
      name
    })
    df.show
    //dataFrame转RDD: df.rdd
    val rdd2 = df.rdd
    rdd2.map(row=>{
      val sex = row.getAs[String]("sex")
    })
    //rdd转dataset: toDS方法
    val ds = rdd.toDS()
    ds.show
    //dataset转rdd: ds.rdd
    val rdd3 = ds.rdd

    //dataset转dataFrame
    val df2 = ds.toDF()
    df2.show
    //dataFrame转dataSet
    val ds3 = df2.as[Person]
    val ds4 = df2.as[(String,Int,String)]
    ds3.show
    ds4.show

  }
}
