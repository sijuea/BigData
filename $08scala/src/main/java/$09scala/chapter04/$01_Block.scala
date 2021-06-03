package $09scala.chapter04

object $01_Block {

  /**
    * java的流程控制: if语句、for、while、do-while、switch
    * scala没有switch
    * scala的流程控制: if语句、for、while、do-while
    *
    * 块表达式:  由{ }包裹的一段代码称之为块表达式,块表达式有返回值,返回值是{ }中最后一个表达式的结果值
    *  后续只要看到{ }基本上就可以认定为块表达式,当然for、while、do-while例外
    */
  def main(args: Array[String]): Unit = {

    val b = {
      println(".......")
      val a = 10
      val b =20
      val c = a+b
      println(c)
      c
    }

    println(b)
    20
  }
}
