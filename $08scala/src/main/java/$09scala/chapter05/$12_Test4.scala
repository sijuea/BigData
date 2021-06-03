package $09scala.chapter05

object $12_Test4 {

  /**
    * 4、根据指定的规则获取数组中最大元素
    *   Array("zhangsan 30 3500","lisi 25 1800","zhaoliu 29 4500")
    *   规则: 获取工资高的人的信息
    *   结果: "zhaoliu 29 4500"
    */
  def main(args: Array[String]): Unit = {

    val data =  Array("zhangsan 30 3500","lisi 25 1800","zhaoliu 29 4500")

    val func = (x:String) => x.split(" ")(2).toInt
    println(maxBy(data, func))

  }

  def maxBy(data:Array[String], func: String => Int) = {

    //获取第一个元素的规则字段
    var tmp = func( data(0) )
    var tmpElement = data(0)
    //遍历
    for(element<- data){
      //得到按照哪个字段取最大值
      val ele = func(element)
      if(ele > tmp){
        tmp = ele
        tmpElement = element
      }

    }

    tmpElement

  }
}
