package $09scala.chapter07

object $05_ImmutableSet {

  /** Set特点: 不重复，无序
    * 不可变Set的创建: Set[元素类型](初始元素,初始元素,...)
    *
    */
  def main(args: Array[String]): Unit = {

    val set1 = Set[Int](3,3,2,7,1,10)
    println(set1)

    //添加元素
    val set2 = set1.+(5)

    val set3 = set1.++(List(20,50,30))
    val set4 = set1.++:(List(20,50,30))
    println(set2)
    println(set3)
    println(set4)

    //删除元素
    val set5 = set1.-(3)
    println(set5)

    val set6 = set1.--(List(2,1))
    println(set6)

    //获取元素

    //修改元素
  }
}
