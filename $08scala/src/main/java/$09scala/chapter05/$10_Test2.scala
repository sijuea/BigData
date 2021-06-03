package $09scala.chapter05

object $10_Test2 {

  /**
    * 2、对数据中的元素按照指定的规则进行过滤
    * Array(1,4,7,9,10,6,8)
    * 规则: 只保留偶数数据
    * 结果: Array(4,10,6,8)
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val data = Array(1,4,7,9,10,6,8)

    val func = (x:Int) => x%2==0
    val func2 = (x:Int) => x%2!=0

    println(filter(data, func2).toList)
    //直接传递函数值
    println(filter(data, (x:Int) => x%2!=0).toList)
    //省略函数参数类型
    println(filter(data, (x) => x%2!=0).toList)
    //函数参数只有一个,省略()
    println(filter(data, x => x%2!=0).toList)
    //用_代替
    println(filter(data, _%2!=0).toList)
  }

  def filter(data:Array[Int], func: Int => Boolean) = {

    for(element<- data if( func(element) )) yield{
        element
    }
  }
}
