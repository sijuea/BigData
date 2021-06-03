package $09scala.chapter05

object $06_HightFunction {

  /**
    * 高阶函数定义: 以函数作为参数或者返回值的方法/函数称之为高阶函数
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val func = (x:Int,y:Int) => {
      x+y
    }
    //方法就是函数
    def m1(x:Int,y:Int):Int = x*y

    println(add(10, 20, func))

    println(add(100, 200, m1))
  }

  /**
    * 高阶函数
    */
  def add(x:Int,y:Int, func: (Int,Int)=>Int ) = {

    func(x,y)

  }
}
