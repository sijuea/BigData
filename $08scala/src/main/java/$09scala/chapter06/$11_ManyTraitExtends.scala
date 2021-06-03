package $09scala.chapter06

object $11_ManyTraitExtends {

  trait ParentLog {
    def add(x:Int,y:Int) = {
      println("ParentLog..............")
      x*x+y*y
    }
  }

  trait Log extends ParentLog{

    override def add(x:Int,y:Int) ={
      println("Log..............")
      val k = x+y
      //super.add(k,x)
      k
    }
  }


  trait Log2 extends ParentLog{
    override def add(x:Int,y:Int) = {
      println("Log2..............")
      val k = x-y
      super.add(k,x)
    }

  }

  trait Log3 extends ParentLog{
    override def add(x:Int,y:Int) = {
      println("Log3..............")
      val k = x*y
      super.add(k,x)
    }
  }

  class WarnLog extends Log with Log2 with Log3{

    override def add(x: Int, y: Int): Int = {
      println("WarnLog..............")
      val k = x/y
      super.add(k,x)
    }
  }

  /**
    * scala子类可以实现多个trait，如果这多个trait中有同名方法,并且参数列表也一样,此时创建子类对象之后调用该方法默认会报错，所以需要子类中重写该同名方法
    *     子类重写之后要想调用trait的同名方法,需要通过super关键字调用,默认调用的是继承顺序最后一个特质的同名方法，要想调用指定特质的同名方法可以通过 super[特质名].同名方法
    *
    * 如果子类继承的多个trait都有一个公有的父trait,然后再同名方法中都有通过super调用同名方法,此时方法的执行顺序是按照继承的顺序从右向左开始调用
    */
  def main(args: Array[String]): Unit = {

    val log = new WarnLog
    println(log.add(10, 20))
  }
}
