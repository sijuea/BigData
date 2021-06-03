package com.demo.tdemo.day07

import org.apache.spark.sql.SparkSession
import org.junit.Test

class $02_DattaFrameMode {

  val spark = SparkSession.builder().master("local[4]").appName("test").getOrCreate()

  import spark.implicits._

  /**
    * sparksql的编程:
    *     1、命令式: 通过api算子操作数据
    *     2、声明式: 通过sql语句操作数据
    */

  /**
    * 命令式
    */
  @Test
  def command(): Unit ={

    val list: List[Person] = List( Person("lisi",20,"shenzhen"),Person("wangwu",30,"beijing"),Person("wangwu",30,"beijing"),Person("wangwu",50,"shanghai"),Person("zhaoliu",25,"shenzhen"),Person("hanmeimei",55,"shenzhen"),Person("lilei",35,"beijing") )


    val df = list.toDF()

    //需求: 统计年龄小于的30的人的信息
    df.where("age<=30").select("name","age","address").groupBy("address").min("age").show

    //常用的命令式:
    //selectExpr: 列裁剪[选择需要的字段、写sql函数]<常用>
    // select: 中只能写列名，不能使用函数
    val df2 = df.selectExpr("name","age","address")

    df2.selectExpr("min(age)").show

    //filter/where: 过滤数据
    df.filter("age<=25").show()
    df.where("age<=25").show()

    //distinct: 去重[全部列都相同才算重复]
    df.distinct().show()

    //dropDuplicates: 去重[指定列相同就算重复]
    df.dropDuplicates("name").show


  }

  /**
    * 声明式: 通过sql语句操作数据 <常用>
    *     1、注册表:
    *         createOrReplaceTempView: 将DF注册成表,如果表存在则会替换,该方式创建的表只能在当前sparksession中使用<工作常用>
    *         createOrReplaceGlobalTempView: 将DF注册成表,如果表存在则会替换,该方式创建的表可以在当前application中多个sparksession中使用。后续要使用的时候表名前面必须加上global_temp
    *     2、sql语句操作数据: spark.sql(.....)
    */
  @Test
  def sqlcommand(): Unit ={

    val list: List[Person] = List( Person("lisi",20,"shenzhen"),Person("wangwu",30,"beijing"),Person("wangwu",30,"beijing"),Person("wangwu",50,"shanghai"),Person("zhaoliu",25,"shenzhen"),Person("hanmeimei",55,"shenzhen"),Person("lilei",35,"beijing") )

    val df = list.toDF()

    //注册表
    //df.createOrReplaceTempView("person")
   df.createOrReplaceGlobalTempView("person")

    spark.sql(
      """
        |select name,age,address
        | from global_temp.person
      """.stripMargin).show()

    val spark2 = spark.newSession()
    spark2.sql("select * from global_temp.person").show
  }
}
