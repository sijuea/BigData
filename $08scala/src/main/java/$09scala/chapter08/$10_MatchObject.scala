package $09scala.chapter08

object $10_MatchObject {

  case class Person(name:String,var age:Int,address:String){

    def add(x:Int,y:Int) = x+y
  }
  /**
    * 样例类语法: case class 类名([val/var] 属性名:属性类型)
    *     属性名不用val/var修饰的时候,默认就是val
    * 样例类其实就是伴生对象和伴生类的封装
    * 创建样例类的对象: 类名(属性值,...)
    *
    * 普通类对象默认不能直接用于模式匹配,如果要想用于模式匹配,需要在伴生对象中定义unapply方法将对象解构成属性
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val person = Person("zhangsan",20,"beijing")
    println(person.name)
    println(person.age)
    println(person.address)

    Student("xx",100)

    println(person.add(10, 20))

    person match{
        
      case Person(x,y,z) => println(s"${x} ${y} ${z}")
    }

    val student = new Student("lisi",20)

    student match{
      case Student(name,age) => println(s"${name} ${age}")
    }

  }
 
  
  
}
class Student(val name:String,var age:Int)
object Student{
  def apply(name: String,age: Int): Student = new Student(name,age)

  def unapply(arg: Student): Option[(String, Int)] = {
    if( arg==null) None
    else
      Some( (arg.name,arg.age) )
  }
}
