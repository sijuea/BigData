package $09scala.chapter06

import java.util.{HashMap => JavaHashMap}

import scala.collection.immutable.HashMap

object $06_Package {

  private[chapter06] val address = "beijing"
  /**
    * 包的作用:
    *   1、便于管理
    *   2、便于区分同名类
    * java中包的用法:
    *     1、导入包: import 包名.类/import 包名.*
    *     2、声明包： package com.atguigu.chapter06
    * scala中包的用法:
    *     1、导入包:
    *         1、导入包下所有类: import 包名._
    *         2、导入包下某个类: import 包名.类
    *         3、导入包下某几个类: import 包名.{类名,类名,..}
    *         4、导入包下某个类,并起别名: import 包名.{类名=>别名,...}
    *         5、导入包下除开某个类的所有类: import 包名.{类名=>_,_}
    *     2、声明包: package com.atguigu.chapter06
    *     3、创建包: package 包名{ .....} [此种方式创建的包只能在target目录才能看到,当前项目结构下看不到]
    *     4、包对象:
    *         语法: package object 包名{....}
    *         包对象中定义的非private修饰的的变量/方法/函数可以在包下任何地方使用
    *     5、与访问修饰符结合:
    *         语法: private[包名]  标识修饰的属性/方法只能在指定包下使用,其他包不能使用
    */
  def main(args: Array[String]): Unit = {

    val map = new JavaHashMap[String,Int]()

    val person = new Person
    println(person)

    println(XXXX)

    Hello()
  }
}

class Person
