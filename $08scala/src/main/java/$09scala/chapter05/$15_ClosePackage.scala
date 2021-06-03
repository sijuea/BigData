package $09scala.chapter05

object $15_ClosePackage {

  /**
    *
    *  闭包: 函数体中使用了外部变量的函数称之为闭包
    */
  def main(args: Array[String]): Unit = {

    val a = 100

    //闭包
    val func = (x:Int) => {
      x+a
    }

    println(func(20))

  }
}
