package $09scala.chapter12

import scala.beans.BeanProperty

object $05_GenericContext {

  class Person[T](@BeanProperty var name:T)

  implicit val p = new Person[String]("xx")

  /**
    * 泛型上下文: def 方法名[A:具体类型](参数名:A) == def 方法名[A](参数名:A)(implicit 参数名: 具体类型[A])
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //println(m1[String]("lisi").getName)
    println(m2[String]("lisi").getName)
  }

  def m2[T:Person] (name:T) = {
    val p = implicitly[Person[T]]
    p.setName(name)
    p
  }

  def m1[U](name:U)(implicit p:Person[U]) = {
    p.setName(name)
    p
  }


}
