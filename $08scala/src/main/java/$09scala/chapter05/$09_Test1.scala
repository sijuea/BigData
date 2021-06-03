package $09scala.chapter05

object $09_Test1 {

  /**
    * 1、对数组中的每个元素进行操作,操作规则由外部决定
    * Array("spark","hello","java","hadoop")
    * 规则: 获取每个元素的长度 [不确定]
    * 结果: Array(5,5,4,6)
    */
  def main(args: Array[String]): Unit = {

    val arr = Array("spark","hello","java","hadoop")

    val func = (x:String) => x.length
    val func2 = (x:String) => x.charAt(0)

    println(map(arr, func2).toList)
    //直接传递函数值
    println(map(arr, (x:String) => x.length).toList)
    //省略函数类型
    println(map(arr, (x) => x.length).toList)
    //省略()
    println(map(arr, x => x.length).toList)
    //使用_代替
    println(map(arr, _.length).toList)

  }


  def map(arr:Array[String], func: String => Any ) = {

    for(element<- arr) yield {
      val result = func(element)
      result
    }
  }

}
