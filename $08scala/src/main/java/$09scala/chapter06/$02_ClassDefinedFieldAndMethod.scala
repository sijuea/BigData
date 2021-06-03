package $09scala.chapter06

object $02_ClassDefinedFieldAndMethod {

  class Person{

    //定义属性: [修饰符] val/var 属性名:类型 = 值
    // 在class中定义属性的时候可以使用_赋予初始值,使用_赋予初始值的是,属性的类型必须指定
   // private var name:String = _
    //scala中有默认的set/get方法.默认的set方法的方法名就是: 属性名=
    //默认的get方法就是属性名
    //val表示的属性不可修改
    var name:String = _

    var age:Int = _

    val address:String = "shenzhen"

    //属性的java形式的set/get方法
    def setName(name:String) = this.name = name

    def getName() = this.name

    //def setAddress(address:String) = this.address = address

    //scala中定义成员方法的语法: [修饰符] def 方法名(参数名:类型,..):返回值类型 = {...}
    /*private */def printHello() = println("hello....")
  }

  /**
    * 类中定义属性和方法:
    *     scala中定义属性的时候
    */
  def main(args: Array[String]): Unit = {

    val person = new Person

    println(person.age)
    println(person.name)

    person.name=("lisi")
    person.age=(20)

    println(person.age)
    println(person.name)

    println(person.address)

    //person.address="beijing"

    person.printHello()



  }
}
