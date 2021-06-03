package $09scala.chapter05

object $08_NoNameFunction {

  /**
    * 匿名函数定义: 没有函数名的函数称之为匿名函数
    * 匿名函数一般作为值传递给高阶函数
    */
  def main(args: Array[String]): Unit = {

    val func = (x:Int,y:Int) => x+y

    println(func(10, 20))

   // (x:Int,y:Int) => x+y

    println(add(10, 20, (x: Int, y: Int) => x + y))
  }

  def add(x:Int,y:Int, func: (Int,Int)=>Int) = func(x,y)

}
