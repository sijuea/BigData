package $09scala.chapter08

import scala.io.StdIn

object $03_MatchValue {
  /**
    * 语法:
    *   变量 match{
    *     case 值 => ..
    *     case 值 => ..
    *     case 值 => ..
    *     case 值 => ..
    *   }
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val word = StdIn.readLine("请输入一个单词:")

    val Hello = "10"
    //如果匹配条件中需要使用外部的变量,此时需要将外部变量首字母大写
    word match {
      case "hadoop" => {
        println("hadoop...........")
      }
      case "spark" => {
        println("spark...........")
      }
      case "flume" => {
        println("flume...........")
      }
      case Hello => {
        println(s"${Hello}")
        println("其他。。。。。。。。。。。")
      }
      case x => {

      }
    }
  }
}
