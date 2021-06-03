package $09scala.chapter08

object $06_MatchList {

  def main(args: Array[String]): Unit = {

    val list: List[Int] = List( 1 ,4,7 )
    //第一种方式:
    list match {
        //匹配List只有一个元素
      case List( x ) =>  println("List只有一个元素")
      //匹配List至少有一个元素,该元素类型是Int
      case List(x:Int,_*) => println("List至少有一个元素,该元素类型是Int")
        //匹配List有三个元素
      case List( x,y,z ) =>  println("List有三个元素")
        //匹配List至少有一个元素
      case List( x,_* ) =>  println("List至少有一个元素")
      case _ => println("其他...")
    }
    println("*"*100)
    //第二种方式
    list match{
      case x :: Nil =>  println("List只有一个元素")
        //匹配至少有两个元素,第一个元素的类型为Int
      case (x:Int) :: y :: tail => println("List至少有一个元素，第一个元素的类型为Int")
        //匹配至少有两个元素
      case x :: y :: tail => println("List至少有一个元素")
      case x :: y :: z ::  Nil =>  println("List有三个元素")
    }

    //泛型的特性: 泛型擦除
    //普通的泛型代表只是在代码编写的时候规范数据类型的
    //泛型在代表编译执行的时候会擦掉
    list match {
      case x:List[Int]=> println("List[Int]")
      case x:List[Any]=> println("List[Any]")
      case x:List[String]=> println("List[String]")
    }
  }
}
