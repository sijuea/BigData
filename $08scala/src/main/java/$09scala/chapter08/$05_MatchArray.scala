package $09scala.chapter08

object $05_MatchArray {

  def main(args: Array[String]): Unit = {

    val arr = Array[Int](1,3,6,10)

    arr match {
        //匹配数组只有一个元素
      case Array(x) => println(s"数组只有一个元素,元素是${x}")
        //匹配数组有三个元素
      case Array(_,y,z) => println(s"数组有三个元素,元素是 ${y} ${z}")
        //匹配数组至少有一个元素,这一个元素的类型是字符串
      case Array(x:Int,_*) => println("数组至少有一个元素,这一个元素的类型是字符串")
        //匹配数组至少有一个元素
      case Array(x,_*) => println("数组至少有一个元素")

      case _ => println("其他情况...")
    }
    //Array[Int]-> int[]
    //Array[String]-> String[]
    //Array[Any]-> Any[]
    arr match{
      case x:Array[Int] => println("类型是Array[Int]")
      //case x:Array[Any] => println("类型是Array[Any]")
      //case x:Array[String] => println("类型是Array[String]")
    }
  }
}
