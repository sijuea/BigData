package $09scala.chapter04

object $04_WhileAndDoWhile {

  /**
    * scala while、do-while用法与java完全一样
    * while与do-while的区别: while是先判断在执行,do-while是先执行在判断
    */
  def main(args: Array[String]): Unit = {

    var a = 11
    while(a<=10){
      println(s"a=${a}")
      a=a+1
    }
    println("*"*40)
    var b =11
    do{
      println(s"b=${b}")
      b=b+1
    }while(b<=10)
  }
}
