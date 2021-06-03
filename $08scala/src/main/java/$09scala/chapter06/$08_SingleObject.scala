package $09scala.chapter06

object $08_SingleObject {

  val name = "lisi"
  /**
    * scala中object表示的就是单例对象
    * 如何获取单例对象:  object名称
    * object里面的属性与方法都是类似java static修饰的,所以可以通过 object名称.属性/方法 的形式调用
    * class里面的属性和方法都是类似java 非static修饰的,所以只能通过 对象.属性/方法 的形式调用
    *
    */
  def main(args: Array[String]): Unit = {

    println($08_SingleObject)
    println($08_SingleObject)

    println($08_SingleObject.name)

  }
}
