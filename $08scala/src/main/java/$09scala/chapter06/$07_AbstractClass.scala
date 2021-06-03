package $09scala.chapter06

object $07_AbstractClass {

  abstract class Animal{

    //具体属性
    val age = 20

    //抽象属性
    val name:String
    //抽象方法
    def add(x:Int,y:Int):Int

    //具体方法
    def hello() = println("....")
  }

  class Pig extends Animal{

    override val name: String = "liis"

    //重写抽象方法
    override def add(x: Int, y: Int): Int = x+y
  }

  /**
    * java中定义抽象类: 修饰符 abstract class 类名{...}
    * java中定义抽象方法: 修饰符 abstract 返回值类型 方法名(参数类型 参数名,...)
    *
    * scala中定义抽象类: abstract class 类名{..}
    * scala中定义抽象方法: def 方法名(参数名:参数类型,...):返回值类型
    * 抽象属性: 没有初始化的属性称之为抽象属性
    * 抽象方法: 没有方法体的方法称之为抽象方法
    *     在定义抽象方法的时候,抽象方法的返回值必须定义,如果没有定义默认是Unit
    * 具体方法： 有方法体的方法称之为具体方法
    * 具体属性: 有初始化的属性称之为具体属性
    */
  def main(args: Array[String]): Unit = {

    val pig = new Pig
    println(pig.add(10, 20))
    println(pig.name)

    //匿名子类
    val animal = new Animal {

      override val name: String = "aa"

      override def add(x: Int, y: Int): Int = x-y
    }
  }
}
