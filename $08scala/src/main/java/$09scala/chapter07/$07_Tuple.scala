package $09scala.chapter07

object $07_Tuple {

  /**
    * 元组的创建方式:
    *     1、通过()方式创建: (初始元素,初始元素,...)
    *     2、通过->方式创建[此种方式只能用于创建二元元组]: K -> V
    *          scala中的二元元组表示KV键值对
    *     元组中最多只能放22个元素
    *     元组一旦定义长度以及元素都不可改变
    *
    *    元组查看元素: 元组名._角标 [元组的角标从1开始]
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {
    //1、通过()方式创建: (初始元素,初始元素,...)
    val t = ("zhangsan",20,"address")
    //2、通过->方式创建[此种方式只能用于创建二元元组]: K -> V
    val t2 = "LISI"->20

    println(t._1)
    println(t._2)
    println(t._3)

    val regions = List(
      ( "宝安区",( "宝安中学", ("大数据班", ( "zhangsan",30 )) ) ),
      ( "宝安区",( "宝安中学", ("大数据班", ( "lisi",30 )) ) ),
      ( "宝安区",( "宝安中学", ("大数据班", ( "wangwu",30 )) ) )
    )

    //需求： 获取每个学生的姓名
    for( region <- regions){
      //
      println(region._2._2._2._1)
    }
  }
}
