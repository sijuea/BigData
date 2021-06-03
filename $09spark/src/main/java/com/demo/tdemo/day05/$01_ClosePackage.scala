package com.demo.tdemo.day05

object $01_ClosePackage {

  //闭包: 使用了外部变量的函数称之为闭包
  def main(args: Array[String]): Unit = {

    val a  = 10

    val func = (x:Int) => {
      x + a
    }

    println(func(10))
  }


}
