package $09scala.test.function

/**
 * @author sijue
 * @date 2021/4/19 14:13
 * @describe
 */
object WordCountExample {
  //普通wordCount
  def wordCount(arr:List[String]):Map[String,Int]={
    val array = arr.flatMap(x => x.split(" "))
    val map = array.groupBy(x => x)
    val result = map.map(key => (key._1, key._2.size))
    result
  }

  def wordCountHigh(list:List[(String,Int)]):Map[String,Int]={
    val list1 = list.flatMap(x => {
      val tuples = x._1.split(" ").map(y => (y, x._2))
      tuples
    })
    val map = list1.groupBy(x => x._1)
    val iterable = map.map(x => {
      val value = x._2
      val ints = value.map(x => x._2)
      (x._1,ints.sum)
    })

    list.flatMap(x=>x._1.split(" ").map((_,x._2))).groupBy(_._1).map(x=>(x._1,x._2.map(_._2).sum)).foreach(println(_))
    iterable
  }
  def main(args: Array[String]): Unit = {
  //普通wordCount
    val stringList = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")
    val map1 = stringList.flatMap(x => x.split(" ")).groupBy(x => x).map(x => (x._1, x._2.size))
    val map = wordCount(stringList)
    map.foreach(println(_))
  //进阶wordCount
    val tuples = List(("Hello Scala Spark World", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))
    tuples.flatMap(x=>x._1.split(" ").map((_,x._2))).groupBy(_._1).map(x=>(x._1,x._2.map(_._2).sum)).foreach(println(_))

  }

}
