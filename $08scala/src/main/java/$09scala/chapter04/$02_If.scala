package $09scala.chapter04

object $02_If {

  /**
    * java的if的使用方式:
    *     1、双分支: if .. else..
    *     2、多分支: if .. else if.. else
    *     3、单分支: if ..
    * scala的if语法与java if的语法完全一样。
    * scala的if使用:
    *     1、双分支: if .. else..
    *     2、多分支: if .. else if.. else
    *     3、单分支: if ..
    *java中if语句没有返回值的,scala的if有返回值,返回值是符合条件的分支的{ }中最后一个表达的结果值
    */
  def main(args: Array[String]): Unit = {

    val a = 10
    //单分支
    if(a%2==0){
      println("a是2的倍数")
    }

    //多分支
    if(a%3==0){
      println("a是3的倍数")
      4
    }else if(a%4==0){
      println("a是4的倍数")
      5
    }else{

      println("a是其他")
      if(a%5==0){
        println("....a是5的倍数")
        10
      }else{
        20
      }

    }

    //双分支
    val e = if(a%5==0){
      println("a是5的倍数")
      val c = 10
      //val d = a+c
      a+c
    }else{
      println("a是其他")
      ""
    }

    println(e)



  }
}
