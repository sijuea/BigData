package $09scala.chapter05

object $01_MethodDefined {

  /**
    * 方法定义语法: def 方法名(参数名:类型,...):返回值类型 = {方法体}
    * scala中方法可以定义在任何地方
    */
  def main(args: Array[String]): Unit = {

    def add(x:Int,y:Int):Int = {
      x+y
    }
    println(add(10, 20))
  }

/*  def add(x:Int,y:Int):Int = {
    x+y
  }*/
}
