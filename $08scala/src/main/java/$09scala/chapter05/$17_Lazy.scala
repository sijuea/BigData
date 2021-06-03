package $09scala.chapter05

object $17_Lazy {

  /**
    * 惰性求值: 变量不会立即初始化,而是等到真正使用的时候才会初始化
    *   语法:  lazy val 变量名:类型 = 值
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val name = "lsii"

    println(name)

    lazy val age = 20

    println(age)

    println("-----------------")
  }
}
