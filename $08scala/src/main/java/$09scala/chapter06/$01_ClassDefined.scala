package $09scala.chapter06

object $01_ClassDefined {

  class Person

  /**
    * java中创建类: [修饰符] class 类名{...}
    * scala中创建类: class 类名{...}
    *
    * java中创建对象: new 类名(...)
    * scala中创建对象: new 类名(..)
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //如果使用无参构造器创建对象,()可以省略
    //val person = new Person
    val person = new Person()
    println(person)
  }
}
