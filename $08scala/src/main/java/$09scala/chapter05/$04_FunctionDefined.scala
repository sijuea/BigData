package $09scala.chapter05

object $04_FunctionDefined {

  /**
    * 函数的语法: val 函数名 = (参数名:参数类型,...) => { 函数体 }
    * 函数简化: 如果函数体中只有一行语句,{ }可以省略
    * 函数调用的时候必须带上()
    *
    * 方法就是函数,函数也是对象
    */
  def main(args: Array[String]): Unit = {

    println(add(10, 20))
    println(add2(10, 20))

    //
    println(printHello)

    val add5 = add2
    println(add5(100, 200))

    println(add4.apply(100, 200))
    println(add4(100, 200))

    var add10 = add
    println(add10(50, 50))
    add10 = add2

    println(add10(100, 100))
    //println(add(v1 = 10, v2 = 20))
  }

  //定义函数
  val add = (x:Int,y:Int) => {
    x+y
  }

  val add2 = (x:Int,y:Int) =>  x+y

  val printHello = () =>  println("hello....")

  val add4 = new Function2[Int,Int,Int] {
    //函数的计算逻辑
    override def apply(v1: Int, v2: Int): Int = {
      v1+v2
    }
  }

}
