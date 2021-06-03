package $09scala.chapter06

object $15_NewType {

  /**
    * 新类型: 给指定类型起别名，后续变量类型..就可以使用该别名
    * 语法:   type 别名 = 类型
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val name:String = "zhangsan"

    type MyString = String

    val address:MyString = "beijing"

    println(address)
  }
}
