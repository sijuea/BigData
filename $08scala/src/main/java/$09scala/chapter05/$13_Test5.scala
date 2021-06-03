package $09scala.chapter05

object $13_Test5 {

  /**
    * 5、根据指定规则对数组所有元素聚合
    * Array(10,4,6,10,2)
    * 规则: 求和/求乘积
    * 结果: 32
    *
    */
  def main(args: Array[String]): Unit = {

    val datas = Array(10,4,6,10,2)

    println(reduce(datas, (agg: Int, curr: Int) => agg * curr))
  }

  def reduce(data:Array[Int],func: (Int,Int)=>Int) = {
    //上一次聚合结果
    var agg = data(0)

    for(i<- 1 until(data.length)) {

      agg = func(agg, data(i) )
    }

    agg
  }
}
