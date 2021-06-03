package $09scala.chapter08

import scala.io.StdIn

object $01_MatchDefined {

  /**
    * 模式匹配语法:
    *     变量 match {
    *        case 条件 => {....}
    *        case 条件 => {....}
    *        .....
    *        case x => {..}  //默认其他的情况
    *     }
    * case条件后面的{ }可以省略
    * scala模式匹配有返回值
    */
  def main(args: Array[String]): Unit = {

    val word = StdIn.readLine("请输入一个单词:")

    val result = word match {
      case "hadoop"=>
        val a = 10
        val c = a+10
        println(c)
        println("输入的是hadoop")
        10


      case "spark" =>{
        println("输入的是spark")
        20
      }
      case "flume"=>{
        println("输入的是flume")
        30
      }
        //类似java switch的default
/*      case x => {
        println(s"其他:${x}")
        40
      }*/
        //如果匹配条件的变量在=>右边不使用可以用_代替
      case _ => {
        println(s"其他")
        40
      }


    }

    println(s"最终结果:${result}")

  }
}
