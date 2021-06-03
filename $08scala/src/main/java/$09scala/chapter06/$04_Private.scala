package $09scala.chapter06

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeFilter

import scala.beans.BeanProperty

object $04_Private {

  class Person{

   @BeanProperty/*private*/ var name:String = _

    @BeanProperty/*private*/ var age:Int = _

    /*def setName(name:String) = this.name=name
    def setAge(age:Int) = this.age=age

    def getName() =this.name
    def getAge() = this.age*/
  }

  /**
    * scala为了兼容java的，提供了一个@BeanProperty注解,该注解能够自动给属性生成java的set/get方法
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val person = new Person

    person.setName("lisi")
    person.setAge(20)

    println(person.getName())
    println(person.getAge())

    //将对象转成字符串
    val p = JSON.toJSONString(person, null.asInstanceOf[Array[SerializeFilter]])
    println(p)
  }
}
