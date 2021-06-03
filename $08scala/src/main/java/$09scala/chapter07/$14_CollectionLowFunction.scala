package $09scala.chapter07

object $14_CollectionLowFunction {

  def main(args: Array[String]): Unit = {

    val list = List(10,2,6,4,3)

    //获取最小值
    val min = list.min
    println(min)
    //根据指定条件获取最小值
    val list2 = List("zhangsan 20 300","lisi 15 4000","zhaoliu 30 1500")
    //println(list2.min)
    val func = (x:String) => x.split(" ")(2).toInt
    println(list2.minBy(func))
    //直接传递函数值
    println(list2.minBy((x:String) => x.split(" ")(2).toInt))
    //函数类型可以省略
    println(list2.minBy((x) => x.split(" ")(2).toInt))
    //函数参数只有一个,可以省略()
    println(list2.minBy(x => x.split(" ")(2).toInt))
    //使用_代替
    println(list2.minBy(_.split(" ")(2).toInt))
    //获取最大值
    val max = list.max
    println(max)

    //根据指定条件获取最大值
    println(list2.maxBy(x => x.split(" ")(1)))
    println(list2.maxBy(_.split(" ")(1)))

    //求和
    println(list.sum)

    //排序
    //根据元素本身直接排序[默认升序]
    val list3 = list.sorted.reverse
    println(list3)

    val list4 = list2.sorted
    println(list4)

    //根据指定字段排序[默认升序]【根据函数的返回值排序】
    //sortBy、minBy、maxBy里面的函数是针对集合每个元素操作
    //后续集合元素有多少个,函数就会调用多少次
    println(list2.sortBy(x => x.split(" ")(1).toInt))
    println(list2.sortBy(_.split(" ")(1).toInt))

    println(list.sortBy( x => x))


    implicit val ordering = new Ordering[Int]{
      override def compare(x: Int, y: Int): Int = {
        //降序
        if( x>y ) -1
        else if(x==y) 0
        else 1
        //升序
        /**
          * if( x>y ) 1
          * else if(x==y) 0
          * else -1
          */
      }
    }

    println(list2.sortBy( x => x.split(" ")(1).toInt))

    //根据指定的条件排序[]
    //升序
    println(list.sortWith((x, y) => x < y))
    //降序
    println(list.sortWith((x, y) => y < x))


  }
}
