package $09scala.chapter12

import scala.beans.BeanProperty

object $02_GenericClass {

  class Person[T,U](@BeanProperty var name:T, @BeanProperty var age:U)
  /**
    * 泛型类:
    * class 类名[T,U,P](属性名:T,属性名:U){
    *     def 方法名(参数名:P) = {...}
    * }
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val person = new Person[String,Int]("lisi",20)

    println(person.getName)
    println(person.getAge)
  }
}
