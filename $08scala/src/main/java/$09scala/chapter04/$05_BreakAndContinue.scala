package $09scala.chapter04

import scala.util.control.Breaks._
object $05_BreakAndContinue {

  /**
    * break: 结束循环
    * continue: 结束本次循环，开始下一次循环
    * scala中没有break与continue两个关键字
    *
    * scala实现break与continue
    *     1、导入包: import scala.util.control.Breaks._
    *     2、通过breakable、以及break方法实现break以及continue功能
    *
    */
  def main(args: Array[String]): Unit = {

    //实现break
    /*try{

      for(i<- 1 to 10){
        println(s"i=${i}")
        if(i==5) throw new Exception("跳出循环")
      }
    }catch {
      case e:Exception =>
    }*/
    //scala break实现
    breakable({
      for(i<- 1 to 10){
        println(s"i=${i}")
        if(i==5) break()
      }
    })


    println(".........")


    //实现continue
    /*for(i<- 1 to 10){
      try{

        if(i==5) throw new Exception("...")
        println(s"i=${i}")
      }catch {
        case e:Exception =>
      }
    }*/

    //scala continue实现
    for( i<- 1 to 10){
      breakable({

        if(i==5) break()
        println(s"i=${i}")

      })
    }
  }
}
