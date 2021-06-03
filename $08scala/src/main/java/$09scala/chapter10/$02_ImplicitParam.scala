package $09scala.chapter10

object $02_ImplicitParam {

  /**
    * 隐式转换参数:
    *     语法:
    *         1、定义一个方法,方法需要指定哪个参数后续会由scala悄悄传入
    *             def 方法名(参数名:参数类型,..)(implicit 参数名:参数类型) = {..}
    *         2、定义一个参数,用implicit标识，表明后续该参数可以又scala作为值传递给方法的参数
    *             implicit val 参数名:参数类型 = 值
    */
  def main(args: Array[String]): Unit = {

    import $09scala.chapter01.YY.age1
    println(add1(10))
  }


  def add1(x:Int)(implicit y:Int) = x+y
  def add2(x:Int)(implicit y:Int) = x+y
  def add3(x:Int)(implicit y:Int) = x+y
}
