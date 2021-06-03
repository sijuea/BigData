package $09scala.chapter05

object $14_Currying {

  /**
    * 柯里化: 有多个参数列表的方法称之为柯里化
    */
  def main(args: Array[String]): Unit = {

    println(add2(20, 30)(10)(100))

    val f = add2 _

    val func = add3(20,30)

    val func2: Int => Int = func(10)

    val result: Int = func2(100)

    println(result)

    println(add3(20, 30)(10)(100))
  }

  def add(x:Int,y:Int,z:Int,a:Int) = x+y+z+a
  //柯里化
  def add2(x:Int,y:Int)(z:Int)(a:Int) = x+y+z+a

  //柯里化演变
  def add3(x:Int,y:Int) = {

    /**
      * (z:Int) => {
      *     (a:Int) => x+y+z+a
      * }
      */

      //闭包
    val func = (z:Int) => {

      //闭包
      val func2 = (a:Int) => x+y+z+a

      func2

    }

    func
  }
}
