package $09scala.chapter07

object $17_WordCountHight {

  def main(args: Array[String]): Unit = {

    val tupleList = List( ("Hello Scala Spark World", 4) , ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))

    //1、切分、压平
    //List( (Array(Hello,Scala,Spark,World),4) )
    val splitList = tupleList.flatMap(x=>{
      //x = ("Hello Scala Spark World", 4)
      val arr = x._1.split(" ")
      //Array(Hello,Scala,Spark,World)
      val result = arr.map(y=>(y,x._2))
      //Array(Hello->4,Scala->4,Spark->4,World->4)
      result
    })
    //List( Hello->4,Scala->4,Spark->4,World->4,Hello->3,Scala->3,Spark->3,Hello->2,Scala->2,Hello->1 )
    //2、按照单词分组
    val groupedMap = splitList.groupBy{
      case (key,num) => key
    }
    //Map(
    //    Hello-> List( Hello->4,Hello->3,Hello->2,Hello->1 )
    //    Scala-> List( Scala->4,Scala->3,Scala->2)
    //    Spark-> List( Spark->4,Spark->3)
    //    World-> List( World->4)
    // )
    //3、统计
    val result = groupedMap.map(x=>{
      //x = Hello-> List( Hello->4,Hello->3,Hello->2,Hello->1 )
      //List( Hello->4,Hello->3,Hello->2,Hello->1 )
      val numList = x._2.map(y=> y._2)
      //List( 4,2,2,1 )
      (x._1,numList.sum)
    })
    //4、结果展示
    //Map( Hello-> 10,Scala->9,Spark->7,World->4)
    result.foreach(println(_))

    tupleList.flatMap(x=>x._1.split(" ").map((_,x._2))).groupBy(_._1).map(x=>(x._1,x._2.map(_._2).sum))
      .foreach(println(_))
  }
}
