package $09scala.chapter05

object $18_ControlAbstract {

  /**
    * 控制抽象: 控制抽象是表示一种特殊的类型,该类型不能单独使用只能当做方法的参数使用，控制抽象就是一个块表达式
    *   控制抽象类型表示方式:   => 返回值类型
    *   方法的参数如果是控制抽象,此时可以当做函数调用,调用的时候不能带上()
    * @param args
    */
  def main(args: Array[String]): Unit = {

    /*val b = {
      println("***************")
      val a = 10
      a+20
    }*/

    m1({
      println("***************")
      val a = 10
      a+20
    })
    m1({
      println("***************")
      val a = 10
      a+20
    })
    m1({
      println("***************")
      val a = 10
      a+20
    })

    val func = () =>{
      println("++++++++++++++++++++")
      val a= 10
      a+20
    }


    m1(func())
    m1(func())
    m1(func())

/*    val b: =>Int = {
      10
    }*/
    println("+"*100)
    m2({
      println("***************")
    val a = 10
    a+20
    })
   println("+"*100)
    var a = 0
    loop({
      a<=10
    })({
      println(s"a=${a}")
      a=a+1
      true
    })



  }

  def m1(x:Int) = {
    x+10
  }

  def m2(func: =>Int) = {
    //将控制抽象当做函数调用
    func
    func
  }

  def loop(condition: =>Boolean)(block: => Unit):Unit = {
    if( condition ) {
      block
      loop(condition)(block)
    }
  }
}
