package $09scala.chapter05

object $16_Recursion {

  /**
    * 递归: 自己调用自己称之为递归
    *   递归必须满足两个条件:
    *       1、必须要有退出条件
    *       2、必须定义返回值类型
    * @param args
    */
  def main(args: Array[String]): Unit = {

    println(m1(5))
    println(func(5))
  }

  /**
    * 递归方法
    * @param n
    * @return
    */
  def m1(n:Int):Int = {

    if(n==1) 1
    else n * m1(n-1)
  }

  /**
    * 递归函数
    */
  val func: Int=>Int = (n:Int) => {
    if( n==1 ) 1
    else n * m1(n-1)
  }
}
