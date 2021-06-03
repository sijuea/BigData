package $09scala.chapter02

object $03_ParamDefined {

  /**
    * 变量定义语法:  val/var 变量名:类型 = 值
    * val与var的区别:
    *     1、val修饰的变量,类似java final修饰的变量,不能重新给变量赋值
    *     2、var修饰的变量,可以重新赋值
    * scala中定义变量的时候,变量类型可以省略,省略之后scala会自动推断变量类型
    * scala中定义变量的时候必须初始化。
    */
  def main(args: Array[String]): Unit = {

    val name:String = "zhangsan"

    var age:Int = 20

    //name = "lisi"
    age = 100
    println(name)
    println(age)
   //scala中定义变量的时候,变量类型可以省略,省略之后scala会自动推断变量类型
    val address = "shenzhen"
    println(address)
    //scala中定义变量的时候必须初始化。
    val sex:String = null
  }
}
