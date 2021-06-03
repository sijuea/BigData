package $09scala.chapter05

object $02_MethodSample {

  /**
    * 方法简化原则:
    *     1、如果方法是将方法体{}中最后一个表达式的结果值作为方法的返回值,此时定义方法的时候返回值类型可以省略
    *           如果方法体中有return关键字，那么方法的返回值类型必须定义
    *     2、如果方法体中只有一行语句,那么{}可以省略
    *     3、如果方法不需要返回值,那么=可以省略【=与{}不能同时省略】
    *     4、如果方法不需要参数,那么在定义方法的时候()可以省略
    *         1、如果定义方法的时候省略了(),调用的时候不能有()
    *         2、如果定义方法的时候没有省略(),调用的时候()可有可无
    * @param args
    */
  def main(args: Array[String]): Unit = {

    println(add2(10, 20))
    println(add3(30, 40))
    printHello2("zhangsan")
    //1、如果定义方法的时候省略了(),调用的时候不能有()
    printMsg2
    //2、如果定义方法的时候没有省略(),调用的时候()可有可无
    printMsg
  }

  def add(x:Int,y:Int):Int = {
    x+y
  }
  //1、如果方法是将方法体{}中最后一个表达式的结果值作为方法的返回值,此时定义方法的时候返回值类型可以省略
  def add2(x:Int,y:Int) = {
    x+y
  }
  //2、如果方法体中只有一行语句,那么{}可以省略
  def add3(x:Int,y:Int) = x+y


  def printHello(msg:String):Unit = {
    println(msg)
  }

  //3、如果方法不需要返回值,那么=可以省略[=与{}不能同时省略]
  def printHello2(msg:String){
    println(msg)

  }

  def printMsg():Unit = {
    println("hello......")
  }

  //4、如果方法不需要参数,那么在定义方法的时候()可以省略
  def printMsg2 {
    println("hello......")
  }

  //
  def getNameByName(name:String) = {
    if(name==null) null
    else{
      //....
      "lisi"
    }


  }
}
