package $09scala.chapter10

import com.demo.$09scala.chapter10.$03_ImplicitClass.Student


object $03_ImplicitClass {
/*
  class Person{

    def printHello() = println("Hello....")
  }*/

  implicit class Person(stu:Student){

    def printHello() = println("Hello....")
  }

  class Student{

    def add(x:Int,y:Int) = x+y
  }
  /**
    * 隐式类的语法:
    *     implicit class 类名(属性名:待转换类型) {...}
    * 隐式类其实就是隐式转换方法的一种特殊形式
    * 隐式类必须定义在object/class中,不能置于最顶层
    */
  def main(args: Array[String]): Unit = {

    val student = new Student

    student.printHello
  }

  //implicit def student2Person(stu:Student):Person = new Person(stu)
}
