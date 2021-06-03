package com.demo.tdemo.day05

import java.io.{FileInputStream, FileOutputStream, ObjectOutputStream}

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

import scala.beans.BeanProperty

class $03_SparkSer {

  /**
    * spark序列化包含: Java序列化、Kryo序列化
    *       Java序列化: 类的信息、属性的信息、属性的类型的信息、继承的信息...
    *       Kryo序列化: 值的信息
    * spark默认采用的是Java序列化。Java序列化包含的内容太多,序列化性能比较低。Kryo序列化的信息比较少，性能相比java序列化快了10倍左右
    * 工作中一般推荐使用Kryo序列化。
    * 如何使用Kryo序列化？
    *     1、在创建SparkConf的时候通过设置spark.serializer,配置Kryo序列化
    *     2、注册哪些类使用kryo序列化[可选]: .registerKryoClasses(Array( classOf[Dog] ))
    *         注册与不注册的区别:
    *             注册之后,在序列化的时候不会带上包的信息
    *             不注册的话,在序列化的时候会带上包的信息
    *
    */
  def sparkSer(): Unit ={
    val conf = new SparkConf().setMaster("local[4]").setAppName("test")
      //设置spark默认使用kryo序列化
      .set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
      //指定哪些类使用kryo序列化
//      .registerKryoClasses(Array( classOf[Dog] ))
    val sc = new SparkContext(conf)
  }

  /**
    * java序列化
    */
  @Test
  def javaSer(): Unit ={
    val dog = new Dog
    dog.setName("wangcai")
    dog.setAge(20)

    val oos = new ObjectOutputStream( new FileOutputStream("d:/dog.txt") )
    oos.writeObject(dog)
    oos.flush()
    oos.close()
  }

  /**
    * kryo序列化
    */
  @Test
  def kryoSer(): Unit ={
    val dog = new Dog
    dog.setName("wangcai")
    dog.setAge(20)

    val kryo = new Kryo()

/*    val output = new Output(new FileOutputStream("d:/dog2.txt"))
    kryo.writeObject(output,dog)
    output.flush()
    output.close()*/

    val input = new Input(new FileInputStream("d:/dog2.txt"))
    val dog2 = kryo.readObject(input,classOf[Dog])
    println(dog2.getName)
    println(dog2.getAge)


  }
}

class Dog {
  @BeanProperty
  var name:String = _
  @BeanProperty
  var age:Int = _
}
