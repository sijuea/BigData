package $09scala.chapter02

import scala.io.{Source, StdIn}

object $05_StdIn {

  /**
    * 1、从控制台读取数据： StdIn.readLine()
    * 2、从文件读取数据: Source.fromFile("...").getLines
    */
  def main(args: Array[String]): Unit = {

    val msg = StdIn.readLine("请输入一句话:")
    println(msg)

    println(Source.fromFile("d:/data.txt", "utf-8").getLines().toList)

  }
}
