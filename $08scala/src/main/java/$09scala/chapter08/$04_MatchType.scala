package $09scala.chapter08

import scala.util.Random

object $04_MatchType {

  /**
    * 匹配类型语法:
    *    变量 match{
    *       case x:类型 => ...
    *       case x:类型 => ...
    *       case x:类型 => ...
    *       case x:类型 => ...
    *    }
    * @param args
    */
  class Person
  def main(args: Array[String]): Unit = {

    val arr = Array("spark",2,10.0,false,new Person)

    val index = Random.nextInt( arr.length )

    println(index)

    val element = arr(index)

    element match {
      case x:String if(x.charAt(0)=='s') => println("是字符串")
      case _:Int => println("是Int")
      case _:Boolean => println("是Boolean")
      case _:Person => println("是Person")
      case _:Double => println("是Double")
    }
  }
}
