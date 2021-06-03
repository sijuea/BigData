package $09scala.chapter06

object $05_Extends {

  class Person{

    private val name = "lisi"

    var age = 20

    val address = "shenzhen"

    def add(x:Int,y:Int) = x+y
  }

  class Student extends Person{

    override val address: String = "beijing"

    //override var age = 50

    override def add(x: Int, y: Int): Int = {
      val k = x*y
      super.add(k,x)
    }
  }
  /**
    * java中子类通过extends关键字实现继承
    * java中子类重写父类的方法,通过注解@Overwrite
    * scala中依然还是通过extends关键字实现继承
    *
    * 哪些形式不能被继承?
    *     1、父类中private修饰的成员不能被继承
    *     2、final修饰的class不能被继承
    * 哪些不能被重写: var标识的属性不能被重写
    * scala中子类要想调用父类的方法需要通过super关键字调用
    * scala中子类要想重写父类的方法、val修饰的属性,需要通过override关键字标识
    *
    * 多态: 父类的引用,子类的实例
    *
    */
  def main(args: Array[String]): Unit = {

    val student = new Student

    println(student.add(10, 20))

    println(student.age)
    //println(student.name)

    println(student.address)

    //多态
    val p:Person = new Student
    println(p.add(10, 20))

    println($06_Package.address)


  }
}
