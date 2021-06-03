package $09scala.chapter06

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

import scala.beans.BeanProperty

object $12_Self {

  //A
  trait FileReadAndWrite {

    this:Serializable=>

    //从文件中读取对象
    def readObject() ={

      val ois = new ObjectInputStream( new FileInputStream("d:/obj.txt") )

      val obj = ois.readObject()

      ois.close()

      obj
    }
    //将对象写入文件
    def writeObject(): Unit ={

      val oos = new ObjectOutputStream(new FileOutputStream("d:/obj.txt"))

      oos.writeObject(this)

      oos.close()
    }
  }

  class Person extends FileReadAndWrite with Serializable {

    @BeanProperty var name:String=_

    @BeanProperty var age:Int = _
  }
  /**
    * 特质自身类型: 提醒子类在继承父trait的时候需要提前继承/实现指定的类/trait
    *     语法: this:指定类型=>
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val person = new Person
    person.setName("lisi")
    person.setAge(20)

    person.writeObject()

    val p = new Person
    val obj = p.readObject()
    val p2 = obj.asInstanceOf[Person]
    println(p2.getName)
    println(p2.getAge)
  }
}
