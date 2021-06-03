package $09scala.chapter01

import java.io.File

import scala.io.{BufferedSource, Source}

/**
  * scala中class里面定义的方法、属性都是类似java非static修饰的
  * scala中object里面的定义的属性、方法都是类似java static修饰的
  * java中main: public static void main(String[] args){...}
  * scala中main:  def main(args: Array[String]): Unit
  * scala中没有public关键字,默认就是类似java public效果
  * def 是defined的缩写,def就是方法的标识符
  * args: 参数名
  * Array[String]: 参数类型,Array代表数组,[String]代表数组中元素类型是String
  * Unit: 返回值类型,定义在参数列表后面,与参数列表之间通过:分割,Unit相当于java的void
  *
  * java中每一个语句后面必须有分号,scala中如果一行只有一个语句，那么分号可以省略。如果一行有多个语句,多个语句之间通过;分割
  *
  */
object HelloWorld {

  def main(args: Array[String]): Unit = {

    //java 打印
    System.out.println("hello world....")

    //scala 打印
    println("hello ....")

    //AA.main()
  }
}

object YY{

  implicit def double2Int(d:Double):Int = d.toInt

  implicit def fileToSourceBuffer(file:File):BufferedSource = {

    Source.fromFile(new File("d:/c.txt"),"utf-8")
  }

  implicit val age1:Int = 30
  implicit val age2:Int = 30
}
