package $09scala.chapter07

object $12_CollectionField {

  def main(args: Array[String]): Unit = {

    val list = List(10,3,6,2,9)

    //是否包含某个元素
    println(list.contains(11))

    //获取集合长度
    println(list.length)
    println(list.size)

    //将集合转成字符串
    println(list)
    println(list.mkString("#"))

    //判断集合是否为空
    println(list.isEmpty)
    //转成迭代器
    val it = list.toIterator
  }
}
