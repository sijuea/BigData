package $09scala.chapter12

object $03_GenericLowHight {

  class Parent

  class Sub1 extends Parent

  class Sub11 extends Sub1

  class Sub111 extends Sub11

  /**
    * 上限<泛型必须是指定类型或者是其子类>:   T <: 类型
    * 上限<泛型必须是指定类型或者是其父类>    T >:类型
    * 如果泛型既要上限也要下限,需要先写下限再写上限:  T>:类型1<:类型2
    *
    */
  def main(args: Array[String]): Unit = {

    m1(new Sub111)

    val x:Any = "string"
    m2(x)

    m3(new Sub1)
  }

  //上限
  def m1[T<:Sub1](t:T): Unit ={

  }

  //下限
  def m2[T>:Sub11](t:T): Unit ={

  }

  //上下限同时使用
  def m3[T>:Sub111<:Sub1](t:T): Unit ={

  }
}
