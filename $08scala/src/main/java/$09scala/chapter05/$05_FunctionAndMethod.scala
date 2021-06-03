package $09scala.chapter05

object $05_FunctionAndMethod {

  /**
    * 函数与方法的区别:
    *     1、方法如果定义在class中,是可以重载的。函数不可以重载
    *     2、方法存储在方法区中,函数是对象存储在堆中
    * 函数与方法的联系:
    *     1、方法如果定义在方法中,此时方法就是函数不能重载
    *     2、方法可以转成函数: 方法名 _ <重要>
    * @param args
    */
  def main(args: Array[String]): Unit = {

    println(add(10))
    println(add(100, 200))

    def printHello(msg:String) = println(msg)

    //def printHello() = println("hello....")

    val printHello2 = printHello _

    printHello2("lisi")

  }

  def add(x:Int) = x*x

  def add(x:Int,y:Int) = x+y

  val sum = (x:Int,y:Int) => x+y

  //val sum = (x:Int,y:Int,z:Int) => x+y+z
}
