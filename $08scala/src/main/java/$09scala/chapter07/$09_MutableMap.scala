package $09scala.chapter07

import scala.collection.mutable

object $09_MutableMap {

  /**
    * 可变Map创建:
    *     1、mutable.Map[K的类型,V的类型]( (K,V),(K,V),(K,V) )
    *     2、mutable.Map[K的类型,V的类型]( K->V,K->V,K->V,.. )
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val map1 = mutable.Map[String,Int] ( ("aa",1),("bb",2),("cc",3) )

    val map2  = mutable.Map[String,Int]( "aa"->10,"cc"->20)

    println(map1)
    println(map2)

    //添加元素
    val map3 = map1.+("ee"->30)
    println(map3)

    map1.+=("lisi"->20)
    println(map3)

    val map4 = map1.++( List( "tt"->100,"oo"->200 ))
    println(map4)

    val map5 = map1.++:( List( "tt"->100,"oo"->200 ))
    println(map5)

    map1.++=( List( "tt"->100,"oo"->200 ))
    println(map1)

    map1.put("pp",100)
    println(map1)

    //删除数据
    val map6 = map1.-("oo")
    println(map6)

    map1.-=("oo")
    println(map1)

    val map7 = map1.--(List( "bb","pp","tt"))
    println(map7)

    map1.--=(List( "bb","pp","tt"))
    println(map1)

    map1.remove("cc")
    println(map1)

    //获取数据
    println(map1.getOrElse("lisi_1", 100))

    //修改元素
    map1("aa")=200
    println(map1)

    map1.update("lisi",45)
    println(map1)
  }
}
