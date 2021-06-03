package $09scala.test.function

import java.util

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * @author sijue
 * @date 2021/4/14 15:06
 * @describe
 */
object Test01 {
  //1.求数组中每个字符串的长度
  def lengthArr(array: Array[String])= {
    for (i <- 1 to array.length) yield {
      array(i - 1).length
    }
    /*或者*/
    for (element <- array) yield {
      element.length
    }
  }
  //1：高阶函数实现
  def test01(array: Array[String],func:(String)=>Any)={
    for(element <- array) yield{
      func(element)
//      val res= res
    }
  }

  //2.求数组的偶数
  def ouShu(array: Array[Int])= {
    for(i <- 1 to array.length if(array(i-1).toInt%2==0)) yield{
      array(i-1)
    }
  }
  //高阶函数
  def test02(array: Array[Int],fun:Int=>Boolean)={
    for(element<- array if(fun(element)))yield{
        element
    }
  }
  //3.拆分数组中每个元素，然后根据某个元素分组
  def mapGet(array: Array[String]) :util.HashMap[String,util.ArrayList[String]]={
    val mapNew=new util.HashMap[String,util.ArrayList[String]]();
    for(i <- 1 to array.length ){
      val arraySub=array(i - 1);
      val strings = arraySub.split(" ")(1)
      if(mapNew.containsKey(strings)){
       val value:util.ArrayList[String]= mapNew.get(strings)
        value.add(arraySub)
      }else{
        val list=new util.ArrayList[String];
        list.add(arraySub)
        mapNew.put(strings,list)
      }
      }
      mapNew
      }
  //03 高阶
  def test03(array: Array[String],fun:(String)=>String)={
    val map=new util.HashMap[String,util.ArrayList[String]]()
    for(ele <-array)  {
      val str=fun(ele)
      if(map.containsKey(str)){
        map.get(str).add(ele)
      }else{
        val list=new util.ArrayList[String]()
        list.add(ele)
        map.put(str,list)
      }
    }
    map
  }

  //4.对list元素获取工资最高的元素
  def maxEle(array: Array[String]):String={
    var maxSalary = array(0).split(" ")(1).toInt
    var maxInfo:String=array(0);
    for(element <-array) yield{
      val salary=element.split(" ")(1).toInt
      if(salary>maxSalary){
        maxSalary=salary
        maxInfo=element
      }
    }
    maxInfo
  }

  def test04(array: Array[String],func:String =>String)={
    var max=func(array(0))
    var maxInfo=array(0)
    for(element <- array) {
      val temp=func(element)
      if(temp>max){
        max=temp
        maxInfo=element
      }
    }
    maxInfo

    }
  //5.根据指定规则对数组所有元素聚合;使用高级函数
  val func5=(x:Int,y:Int)=>x+y
  def test5(array: Array[Int],func:(Int,Int)=>Int):Int={
    var sum:Int=array(0);
    for(i <- 1 until array.length){
      sum=func(sum,array(i))
//       sum=sum*array(i)
    }
    sum
  }

  def main(args: Array[String]): Unit = {
    val arr1=Array("spark","hello","java","hadoop");
    val arr2=Array(1,4,7,9,10,6,8);
    val arr3=	Array("zhangsan shenzhen man","lisi beijing woman","zhaoliu beijing man");
    val arr4=Array("zhangsan 30 3500","lisi 25 1800","zhaoliu 29 4500")
    //01 高阶函数实现
    val fun1=(str:String)=>{str.length}
//    println(test01(arr1, fun1).toList)
  //02 高阶
    val fun2=(x:Int)=>{x%2==0}
//    println(test02(arr2,fun2).toList)
  //03 高阶
    val fun3=(str:String) =>str.split(" ")(1)
//    println(test03(arr3,fun3))
  //04高阶
    val fun4=(str:String)=>{str.split(" ")(2)}
    println(test04(arr4,fun4))
    println("*"*80)
    //第一题--------------------------------------
//
//    lengthEle(arr1,func)
//
//    println(lengthArr(arr));
    //第二题-------------------------------------
//
//    println(ouShu(arr2))
    //第三题-------------------------------------
//
//    println(mapGet(arr3))
    //第四题----------------------------
//
//    println(maxEle(arr4))
    //第五题----------------------
//    var arr5=Array(10,4,6,10,2)
//    println((test5(arr5, func5)))
  }
}
