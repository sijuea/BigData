package $09scala.chapter06

object $09_ObjectAndClass {

  /**
    *
    * 伴生类与伴生对象的前提:
    *     1、object与class的名称要一样
    *     2、object与class必须在同一个源文件[.scala]中
    * 伴生类和伴生对象的特性: 伴生类和伴生对象可以互相访问对方的private修饰的成员
    * apply方法:
    *     1、定义位置: 定义在伴生对象中
    *     2、作用: 用于简化伴生类对象的创建
    *     3、定义之后如何得到伴生类对象: 伴生对象名称.apply() / 伴生对象名称()
    */
  def main(args: Array[String]): Unit = {

    val color = new Color
    println(color.getName())

    println(Color.age)

    color.printWeight()

    Color.printName()
    println("-"*100)
    val c1 = Color()
    c1.printWeight()

    val c2 = Color.apply()
    println(c2.getName())

    val c3 = Color("beijing")
    println(c3.getName())
  }
}

class Color(val address:String){

  private val name = "huangse"
  def this() = this("xx")
  def getName() = this.name

  def printWeight() = println(s"${Color.weight}")
}

object Color{

  private val weight = 20


  val age  =100

  def printName() = {
    val color = new Color
    println(s"${color.name}")
  }

  def apply(address:String): Color = new Color(address)

  def apply(): Color = new Color()
}
