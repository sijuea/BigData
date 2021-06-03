package $09scala.chapter07

object $16_WordCountLow {

  def main(args: Array[String]): Unit = {

    val stringList = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")

    //1、切分、压平

    //List(Array(Hello,Scala,Hbase,kafka),Array(Hello,Scala,Hbase),Array(Hello,Scala),Array(Hello))
    //stringList.flatMap(x=>x.split(" "))
    val splitList = stringList.flatMap(_.split(" "))
    //List(Hello,Scala,Hbase,kafka,Hello,Scala,Hbase，Hello,Scala,Hello)

    //2、分组
    val groupMap = splitList.groupBy(x=>x)
    //Map(
    //      Hello-> List( Hello,Hello,Hello,Hello )
    //      Scala-> List( Scala,Scala,Scala)
    //      Hbase-> List( Hbase,Hbase )
    //      kafka-> List( kafka )
    // )

    //3、聚合
    val result = groupMap.map(x=>{
      //x=Hello-> List( Hello,Hello,Hello,Hello )
      (x._1, x._2.size)
    })

    //4、结果展示
    result.foreach(x=>println(x))
    //[
    //   Hello->4,Scala->3,Hbase->2,kafka->1
    // ]

    //stringList.flatMap(_.split(" ")).groupBy(x=>x).map(x=>(x._1,x._2.size)).foreach(println(_))
  }
}
