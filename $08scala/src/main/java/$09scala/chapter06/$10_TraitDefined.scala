package $09scala.chapter06

object $10_TraitDefined {


  trait Log{

    //具体属性
    val name = "lisi"

    //抽象属性
    val age:Int

    //抽象方法
    def add(x:Int,y:Int):Int

    //具体方法
    def printHello() = println("hello.....")
  }

  trait Log2

  class ParentLog

  //子类如果不需要继承父类,在实现第一个特质的时候使用extends关键字实现,后续其他的特质的实现通过with关键字
  class ErrorLog extends Log with Log2 {

    override val age: Int = 20

    override def add(x: Int, y: Int): Int = x+y
  }

  //子类需要继承父类,此时使用extends关键字继承父类,特质的实现使用with关键字
  class WarnLog extends ParentLog with Log with Log2{
    override val age: Int = 10

    override def add(x: Int, y: Int): Int = x-y
  }

  class DebugLog
  /**
    * 特质类似java的接口
    * scala是单继承多实现
    * 特质语法: trait 特质名{...}
    *     特质中既可以定义抽象方法也可以定义具体方法
    *     特质中即可定义抽象属性也可以定义具体属性
    * 特质的实现:
    *     1、子类如果不需要继承父类,在实现第一个特质的时候使用extends关键字实现,后续其他的特质的实现通过with关键字
    *     2、子类需要继承父类,此时使用extends关键字继承父类,特质的实现使用with关键字
    *
    * 特质混入: 指定当前创建的对象可以使用特质的属性和方法
    *     语法: new 类名(...) with 特质名
    */
  def main(args: Array[String]): Unit = {

    val log = new ErrorLog

    println(log.age)
    println(log.add(10, 20))


    val log2 = new DebugLog with Log{
      override val age: Int = 20

      override def add(x: Int, y: Int): Int = x*y
    }
    println(log2.name)
    println(log2.age)
    println(log2.add(10,20))

    val log3 = new DebugLog
    //log3.name

  }
}
