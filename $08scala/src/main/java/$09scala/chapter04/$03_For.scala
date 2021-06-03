package $09scala.chapter04

object $03_For {

  /**
    * java的for循环:
    *   1、普通for循环
    *   2、增强for循环
    * for两个方法:
    *     1、to 方法: start.to(end) 生成的是左右闭合集合
    *     2、until 方法: start.until(end) 生成的是左闭右开的集合
    * for循环基本语法: for( 变量 <- 集合/数组/表达式 ) {...}
    * 守卫: for( 变量 <- 集合/数组/表达式  if(布尔表达式) if(..) ) {...}
    * 步长: for(变量<- start to/until end by step){...}
    *嵌套for循环: for(变量1<- 集合/数组/表达式 ; 变量2<- 集合/数组/表达式 ){..}
    * 嵌入变量: for(变量1<- 集合/数组/表达式 ;  变量名3 = 值 ;变量2<- 集合/数组/表达式 ){..}
    * yield表达式: for( 变量 <- 集合/数组/表达式 ) yield{...}
    */
  def main(args: Array[String]): Unit = {

    //基本for循环
    for(i<- 1 to 10){
      println(i)
    }

    println("*" * 100)
    //for循环+ if判断
    for(i<- 1 to 10){
      //println(s"i*i=${i*i}")
      if(i%2==0){
        if(i%4==0){

          println(s"i=${i}")
        }
      }
    }

    println("+"*100)

    for(j<- 1 to 10 if(j%2==0) if(j%4==0)) {
      println(s"j=${j}")
    }
    println("$"*50)
    //嵌套for循环
    for(i<- 1 to 10){
      if(i%3==0){
        for(j<- 1 to i){
          if(j%2==0)
            println(s"i+j=${i+j}")
        }
      }

    }
    println("*"*10)
    for(i<- 1 to 10 if(i%3==0); j<- 1 to i if(j%2==0)){
      println(s"i+j=${i+j}")
    }
    println("*"*100)
    //嵌入变量
    for(i<- 1 to 10){
      val k = i * i
      for(j<- 1 to k){
        println(s"i+j=${i+j}")
      }
    }
    println("*"*100)
    for(i<- 1 to 10;k=i*i;j<- 1 to k){
      println(s"i+j=${i+j}")
    }

    //yield表达式
    val b = for(i<- 1 to 10) yield {
      val k = i*i
      k
    }

    println(b.toList)

    //(1 to 10).foreach(println(_))

  }
}
