package $09scala.chapter07

import scala.collection.mutable.ArrayBuffer

object $02_MutableArray {

  /**
    * 可变数组
    *     1、创建:
    *         1、通过apply方法: ArrayBuffer[元素类型](初始元素,...)
    *         2、通过new方法: new ArrayBuffer[元素类型]()
    *
    */
  def main(args: Array[String]): Unit = {

    //通过apply方法
    val arr1 = ArrayBuffer[Int](10,2,5,3)
    //通过new方法
    val arr2 = new ArrayBuffer[Int]()

    println(arr1)
    println(arr2)

    //添加元素
    //添加单个元素
    val arr3 = arr1.+:(6)
    println(arr3)
    println(arr1)

    val arr4 = arr1.:+(8)
    println(arr4)

    val arr5 = arr1.+=(20)
    println(arr1)
    println(arr5)
    println(arr1.eq(arr5))

    arr1.+=:(40)
    arr1.+=:(2)
    println(arr1)

    //添加一个集合所有元素
    val arr6 = arr1.++(Array(100,200,300))
    println(arr6)
    println(arr1)

    val arr7 = arr1.++:(Array(400,500))
    println(arr7)

    arr1.++=(Array(600,450))
    println(arr1)

    arr1.++=:(Array(700,350))
    println(arr1)

    //删除元素
    val arr8 = arr1.-(2)
    println(arr8)

    arr1.-=(3)
    println(arr1)

    val arr9 = arr1.--(Array(2,2,5,20))
    println(arr9)

    arr1.--=(Array(2,2,5,20))
    println(arr1)

    arr1.remove(0,3)
    println(arr1)

    //获取元素
    println(arr1(0))

    //修改元素
    arr1(0) = 100
    println(arr1)

    arr1.update(1,500)
    println(arr1)

    //可变数组转不可变数组
    val arr11 = arr1.toArray
    println(arr11.toList)

    //多维数组
    val arr12 = Array.ofDim[Int]( 4,3 )
    println(arr12(0).length)
    println(arr12.length)
  }
}
