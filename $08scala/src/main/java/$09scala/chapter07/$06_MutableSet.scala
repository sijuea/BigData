package $09scala.chapter07

import scala.collection.mutable

object $06_MutableSet {

  /**
    * 可变Set的创建方式: mutable.Set[元素类型](初始元素,...)
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val set1 = mutable.Set[Int](10,3,2,1)
    println(set1)

    //添加元素
    val set2 = set1.+(20)
    println(set2)

    set1.+=(30)
    println(set1)

    val set3 = set1.++(List(100,50,300))
    println(set3)

    val set4 = set1.++:(List(100,50,300))
    println(set4)

    set1.++=(List(100,50,300))
    println(set1)

    set1.add(1000)
    println(set1)
    //删除元素
    val set5 = set1.-(1)
    println(set5)

    set1.-=(30)
    println(set1)

    val set6 = set1.--(List(1,2,3))
    println(set6)

    set1.--=(List(1,2,3))
    println(set1)

    set1.remove(1000)
    println(set1)

    //获取元素

    //修改元素
    set1.update(300,false)
    println(set1)
  }
}
