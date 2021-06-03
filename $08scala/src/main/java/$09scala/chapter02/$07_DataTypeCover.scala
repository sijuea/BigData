package $09scala.chapter02

object $07_DataTypeCover {

  /**
    * 1、数字转字符串
    *     java中数字转字符串: 2 + ""
    *     scala中数字转字符串:
    *       1、拼接: 通过+与插值表达式
    *       2、通过toString方法
    * 2、字符串转数字
    *      java中字符串转数字[String->int]: Integer.valueOf(字符串)
    *      scala中字符串转数字: 字符串.toXXX方法
    * 3、数字与数字的转换
    *      java中低精度转高精度[int->Long]: 自动转换
    *      java中高精度转低精度[Long->int]: 强转
    *
    *      scala中低精度转高精度[int->Long]: 自动转换
    *      scala中高精度转低精度[int->Long]: toXXX方法
    *
    */
  def main(args: Array[String]): Unit = {

    val a:Int = 10

    val b:Long = a

    println(b)

    val c:Int = b.toInt

    val s = "10.0"
    //val d:Int = s.toInt
   // println(d)

    val e:Double = s.toDouble
    println(e)

    //val f:String = e.toString
    val f:String = s"${e}"
    println(f)
  }
}
