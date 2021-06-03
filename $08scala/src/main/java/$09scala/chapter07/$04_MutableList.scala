package $09scala.chapter07

import scala.collection.mutable.ListBuffer

object $04_MutableList {

  /**
    * 可变List的创建方式:
    *     1、apply方法: ListBuffer[元素类型](初始元素,....)
    *     2、new 方式: new ListBuffer[元素类型]()
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {
    //2、new 方式: new ListBuffer[元素类型]()
    val list1 = new ListBuffer[Int]()
    println(list1)
    //1、apply方法: ListBuffer[元素类型](初始元素,....)
    val list2 = ListBuffer[Int](10,3,5,2)
    println(list2)

    //添加元素
    val list3 = list2.+:(4)
    println(list3)

    val list4 = list2.:+(6)
    println(list4)

    list2.+=(20)
    println(list2)

    list2.+=:(30)
    println(list2)

    val list5 = list2.++(Array(100,300,500))
    println(list5)

    val list6 = list2.++:(Array(100,300,500))
    println(list6)

    list2.++=(Array(100,300,500))
    println(list2)

    list2.++=:(Array(100,300,500))
    println(list2)


    //删除元素
    val list7 = list2.-(100)
    println(list7)

    list2.-=(500)
    println(list2)

    val list8 = list2.--(List(100,300,2,5))
    println(list8)

    list2.--=(List(100,300,2,5))
    println(list2)

    list2.remove(0)
    println(list2)

    //获取元素
    println(list2(0))

    //修改元素
    list2(0)=100
    println(list2)

    list2.update(1,300)
    println(list2)

    val list9 = list2.updated(3,2)
    println(list9)
    println(list2)

    //可变List转不可变List
    println(list2.toList)
  }
}
