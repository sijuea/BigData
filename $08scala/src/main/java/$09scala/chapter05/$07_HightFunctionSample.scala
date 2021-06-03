package $09scala.chapter05

object $07_HightFunctionSample {
  /**
    * 高阶函数的简化:
    *   1、直接将函数作为值传递给高阶函数
    *   2、传递函数的时候,函数的参数类型可以省略
    *   3、如果函数的参数在函数体中只使用了一次,那么可以用_代替【第一个_代表函数的第一个参数,第N个下划线代表函数的第N个参数】
    *        一下几种情况不能用_简化:
    *           1、如果函数参数使用顺序与定义的顺序不一致不能用_简化
    *           2、如果函数只有一个参数,并且在函数体中直接将参数返回,此时不能用_代替
    *           3、如果函数体中存在(),函数参数在该()中以表达式的形式存在的时候,此时不能用_代替
    *   4、如果函数只有一个参数,此时函数参数列表的()可以省略
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val func = (x:Int,y:Int) => x*y


    println(add(20, 30, func))
    //1、直接将函数作为值传递给高阶函数
    println(add(20,30,(x:Int,y:Int) => x*y))
    //2、传递函数的时候,函数的参数类型可以省略
    println(add(20,30,(x,y) => x*y))
    //3、如果函数的参数在函数体中只使用了一次,那么可以用_代替
    println(add(20,30,_*_))


    println(add(20,30,(x,y) => y-x))
    //如果函数参数使用顺序与定义的顺序不一致不能用_简化
    println(add(20,30,_-_))

    println("*"*100)

    val func2 = (x:Int) => x*x
    println(m1(200, func2))
    //直接传递函数值
    println(m1(200, (x:Int) => x*x))
    //省略函数参数类型
    println(m1(200,(x)=>x*x))
    //不能用_简化,因为x参数在函数体中使用了两次
    //println(m1(200,_*_))

    println(m1(200,x=>x))
    //如果函数只有一个参数,并且在函数体中没有对参数做任何操作直接返回,此时不能用_代替
    //println(m1(200,_))
    //expanded
    val f = m1(200,_)

    println(f( x => x ))

    // m1(200,_) 调用得到的函数
    val m2 = ( func: (Int) =>Int ) =>{
      func(200)
    }
    println("+"*100)

    println(m1(200,(x)=> ( x+1 )*10 ))
    //如果函数体中存在(),函数参数在该()中以表达式的形式存在的时候,此时不能用_代替
    //println(m1(200,( _ + 1) * 10 )) // println(m1(200,( x=>x+1 ) * 10 ))  x=>x*10

    //如果函数只有一个参数,此时函数参数列表的()可以省略
    println(m1(200,x => ( x+1 )*10 ))


  }

  def add(x:Int,y:Int,func: (Int,Int)=>Int) = {

    func(x,y)
  }

  def m1(x:Int,func: Int =>Int ) = {
    func(x)
  }
}
