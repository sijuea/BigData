package com.demo.training.sparkCore.$04Serializer

import java.io.{FileInputStream, FileOutputStream}

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

import scala.beans.BeanProperty

/**
 * @author sijue
 * @date 2021/4/29 19:21
 * @describe
 */
class RDDSerializer {

def sparkSet= {
  val conf = new SparkConf().
    setMaster("local[4]").setAppName("test")
    .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//      .registerKryoClasses(Array(classOf[ListBuffer[Person1]]))
  val sc = new SparkContext(conf)
}
@Test
  def read() = {
    val input=new Input(new FileInputStream("datas/person.txt"))
  val k=new Kryo();
    val res = k.readObject(input,classOf[Person1])
    input.close()
    println(res)
  }

  @Test
  def write() = {
    val p1 = new Person1("xxx","xx",12)
    p1.setName1("li")
    p1.setAdd1("add")
    p1.setAge1(12)
//    val p2 = new Person1(name="li",add="bej",age=12)
//    val p3 = new Person1("lis3", "be2ijng", 11)
//    val p4 = new Person1("lis3", "be2ijng", 11)
//    val list=ListBuffer(p2, p3, p4)
    //val list=sc.parallelize(List[Peron1](p1, p2, p3, p4))
val k=new Kryo();
    val output=new Output(new FileOutputStream("datas/person.txt"))
    val res = k.writeObject(output,p1)
    output.flush()
    output.close()
  }

  def write(list:RDD[Person1]) = {
    val output=new Output(new FileOutputStream("datas/person.txt"))
    val k=new Kryo();
    val res = k.writeObject(output,list)
    output.flush()
    output.close()
  }

}
 class Person1(var name:String,var add:String,var age:Int){
//   def this()={
//      Person1()
//   }
  @BeanProperty
  var name1:String=_
  @BeanProperty
  var add1:String=_
  @BeanProperty
  var age1:Int=_
}