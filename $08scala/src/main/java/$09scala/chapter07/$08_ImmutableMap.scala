package $09scala.chapter07

object $08_ImmutableMap {

  /**
    * 不可变Map的创建:
    *     1、Map[K的类型,V的类型]( (K,V),(K,V),(K,V) )
    *     2、Map[K的类型,V的类型]( K->V,K->V,K->V,.. )
    *Option: 就是提醒外部当前的结果有可能为空,需要处理
    *     Some: 代表不为空,结果数据封装在Some中
    *     None: 代表为空
    * 取值方式: getOrElse(key,默认值) 【如果key存在则从map中取出key的value值,如果key不存在则返回默认值】
    */
  def main(args: Array[String]): Unit = {

    val map1 = Map[String,Int]("zhangsan"->10,"lisi"->20,"zhaoliu"->15)

    val map2 = Map[String,Int]( ("aa",2),("bb",3),("cc",4),("bb",10) )

    println(map1)

    println(map2)

    //添加元素
    val map3 = map1.+( "hanmeimei"->30 )
    println(map3)

    val map4 = map1.++( List( "cc"->5 , ("dd",15) ) )
    println(map4)

    val map5 = map1.++:( List( "cc"->5 , ("dd",15) ) )
    println(map5)

    //删除元素
    val map6 = map1.-("cc")
    println(map6)

    val map7 = map1.--(List("cc","dd","lisi"))
    println(map7)

    //获取数据
    //println(map1("zhangsan1"))

    //println(map1.get("zhangsan1").get)

    println(map1.getOrElse("zhangsan1", "xx"))

    //修改数据
    //map1("zhangsan") = 30
    val map10 = map1.updated("zhangsan",50)
    println(map10)
  }
}
