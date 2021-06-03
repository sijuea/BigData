package $09scala.chapter07

object $13_CollectionToCollection {

  def main(args: Array[String]): Unit = {

    val list = List(2,4,7,1,9,10,11,11)

    //去重 ******
    val list2 = list.distinct
    println(list2)

    //删除前多少个元素,获取剩下所有元素
    val list3 = list.drop(3)
    println(list3)

    //删除后多少个元素,获取剩下所有元素
    val list4 = list.dropRight(3)
    println(list4)

    //获取第一个元素 ****
    val head = list.head
    println(head)

    //获取最后一个元素 ***
    val last = list.last
    println(last)
    
    //获取除开最后元素的所有元素
    val list5 = list.init
    println(list5)

    //反转 ****
    val list6 = list.reverse
    println(list6)

    //获取子集合
    val list7 = list.slice(0,4)
    println(list7)

    //窗口 sliding(窗口长度,滑动长度) *****
    val list8 = list.sliding(2)
    println(list8.toList)

    //获取除开第一个元素的所有元素 **
    val list9 = list.tail
    println(list9)

    //获取前多少个元素 *****
    val list10 = list.take(3)
    println(list10)

    //获取后多少个元素
    val list11 = list.takeRight(3)
    println(list11)

    //交集: 两个集合都有的元素
    val list12 = List(1,2,3,4,5)
    val list13 = List(4,5,6,7,8)
    val list14 = list12.intersect(list13)
    println(list14)
    //差集[ A差B 的结果就是保留A中有，B中没有的元素]
    val list15 = list12.diff(list13)
    println(list15)
    //并集[不会去重]
    val list16 = list12.union(list13)
    println(list16)

    //拉链
    val list17 = List("aa","bb","cc","dd")
    val list18 = List(1,2,3)
    val list19 = list17.zip(list18)
    println(list19)

    //反拉链
    val t1 = list19.unzip
    println(t1)

    //将元素与角标拉链 ****
    val list20 = list17.zipWithIndex
    println(list20)
  }
}
