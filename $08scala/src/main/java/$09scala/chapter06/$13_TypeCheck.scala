package $09scala.chapter06

import com.alibaba.fastjson.JSON

import scala.beans.BeanProperty
import scala.util.Random

object $13_TypeCheck {

  class Animal

  class Dog extends Animal {
    val name = "xx"
  }

  class Pig extends Animal {
    val weight = "oo"
  }

  def getAnimal()={

    val index = Random.nextInt(10)
    println(index)
    if(index%2==0) new Pig
    else new Dog
  }
  /**
    * 判断对象是否属于某个类型:
    *     java中语法: 对象 instanceof 类型
    *     scala中语法: 对象.isInstanceOf[类型]
    * 将对象强转:
    *     java中语法: (类型)对象
    *     scala中语法: 对象.asInstanceOf[类型]
    *获取类的class形式:
    *     java中语法: 类名.class
    *     scala中语法: classOf[类名] <经常使用>
    *获取对象的class形式:
    *     java中语法: 对象.getClass
    *     scala中语法: 对象.getClass <经常使用>
    *
    */
  def main(args: Array[String]): Unit = {
    val animal = getAnimal

    if(animal.isInstanceOf[Pig]){

      println(animal.asInstanceOf[Pig].weight)

    }else{

      println(animal.asInstanceOf[Dog].name)

    }

    println(animal.getClass)
    println(classOf[Pig])
    println(classOf[Dog])


    val json = """{ "name":"lisi","age":20 }"""

    //将json字符串转成对象
    val person = JSON.parseObject(json,classOf[Person])
    println(person.getName)
    println(person.getAge)
  }

  class Person{
    @BeanProperty
    var name:String = _
    @BeanProperty
    var age:Int = _
  }
}
