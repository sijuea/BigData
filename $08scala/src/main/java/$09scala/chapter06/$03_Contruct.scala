package $09scala.chapter06

object $03_Contruct {

  /**
    * scala的构造方法分为两种: 主构造器、辅助构造器
    *     主构造器:
    *         1、定义位置: 定义在类名后面以()形式表示
    *         2、语法: class 类名( [修饰符] [val/var] 属性名:类型[=默认值] ,.... )
    *         主构造器中val、var修饰的属性与不用val/var修饰的属性的区别:
    *             val、var修饰的非private的属性,可以在外部、内部都可以使用进行使用
    *             不用val/var修饰的属性只能在class内部使用
    *    辅助构造器:
    *       1、定义位置: 定义在class里面
    *       2、语法: def this( 参数名:参数类型,... ) {
    *           //第一行必须调用主构造器或者是其他的辅助构造器
    *         }
    *
    */
  class Person(private var name:String, var age:Int=20, address:String){

    def this(name:String) = {
      this(name,address="北京")
    }

    def getName() = this.name

    def getAddress() = this.address

  }

  /**
    * java的构造方法:
    *     1、定义位置: class里面
    *     2、语法: [修饰符] 类名(参数类型 参数名,...){  }
    *     3、如果没有创建构造方法,有一个默认的无参的构造方法,如果自己创建了构造方法,无参的构造方法默认消失
    *
    *
    */
  def main(args: Array[String]): Unit = {

    //通过主构造器创建对象
    val person = new Person(name = "lisi",address = "beijing")

    //通过辅助构造器创建对象
    val p = new Person("zhangsan")
    println(person)

    println(p.age)
    println(p.getName())
    println(p.getAddress())
  }
}
