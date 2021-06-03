package $09scala.chapter07

object $01_ImmutableArray {

  /**
    * 不可数组：
    *     1、创建:
    *         1、通过apply方法: Array[类型](初始元素,初始元素,...)
    *         2、通过new方式: new Array[类型](数组的长度)
    *     2、添加数据
    *     3、删除数据
    *     4、修改数据: 数组名(角标) = 值
    *     5、获取数据: 数组名(角标)
    * 集合常用的方法的区别:
    *     一个+与两个+的区别:
    *         一个+ 是添加单个元素
    *         两个+ 是将一个集合的所有元素添加进来
    *     冒号在前与冒号在后以及不带冒号的区别:
    *          冒号在前 是将元素添加在集合的末尾
    *          冒号在后 是将元素添加到集合的最前面
    *          不带冒号 是将元素添加在集合的末尾
    *     带+与带-的区别:
    *          带+ 是添加数据
    *          带- 是删除数据
    *     带=与不带=的区别:
    *         带= 修改集合本身
    *         不带= 是原有集合没有改变，而是生成了一个新集合
    */
  def main(args: Array[String]): Unit = {
    //通过apply方法: Array[类型](初始元素,初始元素,...)
    val arr1 = Array[String]("hello","spark","java","scala")
    println(arr1.toList)

    //通过new方式: new Array[类型](数组的长度)
    val arr2 = new Array[String](6)
    println(arr2.toList)

    //添加数据
    //添加单个元素
    val arr3 = arr1.+:("aa")
    println(arr3.toList)
    println(arr1.toList)
    //scala中==与equals作用一样
    println(arr3.eq(arr1))

    val arr4 = arr1.:+("bb")
    println(arr4.toList)
    println(arr1.toList)

    //添加一个集合所有元素
    val arr5 = arr1.++(Array("cc","dd","ee"))
    println(arr5.toList)

    val arr6 = arr1.++:(Array("tt","yy","uu"))
    println(arr6.toList)

    //删除

    //获取数据
    println(arr6(0))

    //修改数据
    arr6(0) = "pp"
    println(arr6.toList)

    //遍历
    for(element<- arr6){
      println(element)
    }

    //不可变数组转可变数组
    val arr11 = arr1.toBuffer
    println(arr11)
  }
}
