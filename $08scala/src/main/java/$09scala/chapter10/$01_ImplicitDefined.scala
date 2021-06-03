package $09scala.chapter10

import java.io.File
import com.demo.$09scala.chapter01.YY._
import scala.io.{BufferedSource, Source}

class XX{

  implicit def double2Int(d:Double):Int = d.toInt

  implicit def double2Int2(d:Double):Int = (d+10).toInt

  implicit def fileToSourceBuffer(file:File):BufferedSource = Source.fromFile(file,"utf-8")

}



object $01_ImplicitDefined /* extends XX*/{

  /**
    * 隐式转换<工作不常用，代码维护性比较差>:
    *     隐式转换方法: 悄悄的将一个类型转为另一个类型
    *         语法: implicit def 方法名(参数名:待转换类型):目标类型 = {..}
    *            隐式转换方法在定义的时候不能省略返回值类型
    *         隐式转换方法的调用时机:
    *             1、当前类型与目标类型不一致的时候会自动调用
    *             2、对象使用了不属于自身的属性和方法的时候会自动调用
    *     隐式参数:
    *     隐式类:
    *
    *隐式转换寻找机制:
    *     1、首先从当前作用域寻找是否有符合要求的隐式转换,如果找到直接自动调用,如果找不到则报错
    *     2、如果隐式转换定义在其他的object中,此时要想使用隐式转换,需要导入: import object名称._ /import object名称.隐式名称
    *     3、如果隐式转换定义在其他的class中,此时首先需要创建一个class的对象,然后通过 import 对象._ / import 对象.隐式转换名称
    *      如果有多个隐式转换都符合要求,此时需要明确指定使用哪一个隐式转换
    * @param args
    */
  def main(args: Array[String]): Unit = {
    val xx = new XX
    //import xx._
    //import YY.double2Int
    //当前类型与目标类型不一致的时import YY.double2Int候会自动调用
    val d:Int = 2.0

    val file = new File("d:/data.txt")
    //import YY.fileToSourceBuffer
    //import xx.fileToSourceBuffer
    //对象使用了不属于自身的属性和方法的时候会自动调用
    file.getLines.foreach(println(_))
  }


}
