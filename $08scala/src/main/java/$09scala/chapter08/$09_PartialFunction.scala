package $09scala.chapter08

object $09_PartialFunction {

  /**
    * 偏函数: 不使用match关键字的模式匹配，称为偏函数
    *     偏函数是一个函数,可以当做函数调用
    * 语法:
    *     val func: PartialFunction[IN,OUT] = {
    *         case 条件 => ...
    *         case 条件 => ...
    *         case 条件 => ...
    *     }
    *     IN: 代表入参类型
    *     OUT: 代表返回值类型
    * @param args
    */
  def main(args: Array[String]): Unit = {
    //定义偏函数
    val func:PartialFunction[String,Int] = {
      case "hadoop" => 10
      case "spark" => 20
      case "flume" => 30
    }

    println(func("hadoop"))

    val regions = List(
      ( "宝安区",( "宝安中学", ("大数据班", ( "zhangsan",30 )) ) ),
      ( "宝安区",( "宝安中学", ("大数据班", ( "lisi",30 )) ) ),
      ( "宝安区",( "宝安中学", ("大数据班", ( "wangwu",30 )) ) )
    )

/*    regions.foreach(x=> {
      x match {
        case (regionName,( schoolName, (className, ( stuName,age)) )) =>
          println(stuName)
      }
    })*/

/*    val func2:PartialFunction[(String,( String, (String, ( String,Int)) )),Unit] = {
      case (regionName,( schoolName, (className, ( stuName,age)) )) =>  println(stuName)
    }*/

    //偏函数的应用场景
    regions.foreach{
      case (regionName,( schoolName, (className, ( stuName,age)) )) =>  println(stuName)
    }

  }
}
