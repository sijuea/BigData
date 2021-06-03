package $09scala

import java.text.SimpleDateFormat

import scala.io.Source

object Test3 {

  //统计每个区域的平均等客时间
  def main(args: Array[String]): Unit = {

    //1、读取数据
    val datas = Source.fromFile("datas/2.txt","utf-8").getLines().toList

    //2、切割、转换时间
    val splitList = datas.map(line=>{
      val arr = line.split("\t")
      val userid = arr.head
      val fromAddr = arr(1)
      val toAddr = arr(2)
      val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val fromTime = formatter.parse(arr(3)).getTime
      val toTime = formatter.parse(arr(4)).getTime
      (userid,toAddr,fromTime,toTime)
    })
    //3、按照司机分组
   splitList.groupBy{
      case (userid,toAddr,fromTime,toTime) => userid
    }
    //Map(
    //    A -> List(
    //      (A,龙岗区,2020-07-15 10:35:15,2020-07-15 10:40:50),
    //      (A,宝安区,2020-07-15 10:05:10,2020-07-15 10:25:02),
    //      (A,龙岗区,2020-07-15 11:33:12,2020-07-15 11:45:35),
    //      (A,宝安区,2020-07-15 11:55:55,2020-07-15 12:12:23),
    //      (A,龙华区,2020-07-15 11:02:08,2020-07-15 11:17:15),
    //      (A,龙岗区,2020-07-15 12:17:10,2020-07-15 12:33:21)
    //     )
    //    ....
    // )
    //4、对每个司机所有数据排序,两两计算
      .toList
      .flatMap{
      case (userid,list) =>
        //按照时间排序
      val sortList = list.sortBy(_._3)
       //List(
        //(A,宝安区,2020-07-15 10:05:10,2020-07-15 10:25:02),
        //(A,龙岗区,2020-07-15 10:35:15,2020-07-15 10:40:50),
        //(A,龙华区,2020-07-15 11:02:08,2020-07-15 11:17:15),
        //(A,龙岗区,2020-07-15 11:33:12,2020-07-15 11:45:35),
        //(A,宝安区,2020-07-15 11:55:55,2020-07-15 12:12:23),
        //(A,龙岗区,2020-07-15 12:17:10,2020-07-15 12:33:21)
        // )
        val slidingList = sortList.sliding(2)
        //List(
        //    List( (A,宝安区,2020-07-15 10:05:10,2020-07-15 10:25:02) , (A,龙岗区,2020-07-15 10:35:15,2020-07-15 10:40:50))
        //    List( (A,龙岗区,2020-07-15 10:35:15,2020-07-15 10:40:50) , (A,龙华区,2020-07-15 11:02:08,2020-07-15 11:17:15))
        //   ...
        // )
        val regionList = slidingList.map(y=>{
          //y = List( (A,宝安区,2020-07-15 10:05:10,2020-07-15 10:25:02) , (A,龙岗区,2020-07-15 10:35:15,2020-07-15 10:40:50))
          val first = y.head
          val next = y.last
          //等客区域
          val region = first._2
          //等客时间
          val duration = (next._3 - first._4)/1000
          (region,duration)
        })
        regionList
    }

    //List( (宝安区,10), (龙岗区,22)，)
    //5、按照等客区域分组
      .groupBy(_._1)
    //Map(
    //  宝安区 -> List( (宝安区,10),(宝安区,15),(宝安区,5).. )
    //  龙岗区 -> List( (龙岗区,10),(龙岗区,15),(龙岗区,5).. )
    // )
    //6、求平均值
      .map{
      case (region,list)=>
        //总等客时间
        val totalTime = list.map(_._2).sum.toDouble

        //总次数
        val num = list.size

        (region,totalTime/num)
    }

    //7、结果展示
      .foreach(println(_))
  }
}
