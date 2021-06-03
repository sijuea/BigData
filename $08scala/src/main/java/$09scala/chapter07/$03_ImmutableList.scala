package $09scala.chapter07

object $03_ImmutableList {

  /**
    * 不可变List创建方式:
    *     1、通过apply方法: List[元素类型](初始元素,...)
    *     2、通过 :: 方法: 初始元素 :: 初始元素 :: .. :: Nil/不可变list集合
    *         Nil就是一个空集合,Nil与不可变List的关系就类似Null与String的关系
    *         Nil一般用于给List类型赋予初始值,但是在赋值的时候必须指定变量的类型
    *         :: 最右边必须是不可变List集合或者是Nil
    * 添加元素:
    *     ::  代表添加单个元素
    *     ::: 代表添加一个集合的所有元素
    * 修改元素:
    *     update与updated的区别：
    *         update是修改集合本身
    *         updated是生成一个新集合
    * @param args
    */
  def main(args: Array[String]): Unit = {
  //通过apply方法: List[元素类型](初始元素,...)
    val list1 = List[Int](10,2,5,8,3)

    println(list1)

    //通过 :: 方法: 初始元素 :: 初始元素 :: .. :: Nil/不可变list集合
    val list2 = 2 :: 3 :: 4 :: Nil
    println(list2)

    val list3 = 2 :: 3 :: Nil
    println(list3)
    println(list2)

    var list:List[Int] = Nil
    list = list1
    var name = null
    //name = "lisi"

    println("*"*100)
    //添加元素

    val list4 = list1.:+( 20 )
    println(list4)

    val list5 = list1.+:(30)
    println(list5)

    val list6 = list.++(Array(100,200,300))
    println(list6)

    val list7 = list.++:(List(300,400,500))
    println(list7)

    val list8 = 800 :: 900 :: list7
    println(list8)

    val list9 = list7 ::: list8
    println(list9)

    //删除元素

    //获取元素
    println(list9(0))

    //修改元素
    //list9(0)=1000
    //println(list9)
    val list10 = list9.updated(0,1000)
    println(list9)
    println(list10)

    //List转成Array
    val arr = list10.toBuffer
    val arr2 = list10.toArray
    println(arr)
  }
}
