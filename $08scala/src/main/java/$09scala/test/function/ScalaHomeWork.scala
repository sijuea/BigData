package $09scala.test.function

import java.text.SimpleDateFormat
import java.time.{LocalDateTime, ZoneId}
import java.time.format.DateTimeFormatter
import java.util.logging.SimpleFormatter

import sun.security.timestamp.Timestamper

import scala.io.Source

/**
 * @author sijue
 * @date 2021/4/19 18:48
 * @describe
 */
object ScalaHomeWork {
  /**
   * 统计哪些省份没有农产品市场
   * @param listFarm
   * @param province
   * @return
   */
  def work1(listFarm:List[String],province:List[String])={
   /**
    val tuples = listFarm.map(x =>{
      val strings = x.split("\t")
      (strings(strings.length-2),strings(strings.length-3))
    } )
    val map = tuples.groupBy(_._1).map(x => (x._1, x._2.size))
    val strings = province.filter(x => {
      !map.contains(x)
    })
    strings
    */

    //处理数据之前判断.是否去重，过滤，列裁剪（截取需要的字段）
    //1.过滤[过滤脏数据]
    var productList=listFarm.filter(x => x.split("\t").length == 6)
      //2.列裁剪【只要省份】&&去重
      .map(x => x.split("\t")(4)).distinct
    //3.获取省份list和产品省份list的差集
      province.diff(productList)
  }

  /**
   * 统计农产品种类数最多的三个省份
   * @param listFarm
   * @return
   */
  def work2(listFarm:List[String]) ={
    /**
    val stringToInt = listFarm.map(x => {
      val strings = x.split("\t")
      (strings(0), strings(strings.length - 2))
    }).groupBy(_._2).map(x => (x._1, x._2.size))
    val tuples = stringToInt.toList.sortBy(x => x._2)(new Ordering[Int] {
      override def compare(x: Int, y: Int): Int = {
        if (x > y) -1
        else if (x == y) 0
        else 1
      }
    })
    tuples.slice(0,3).map(_._1)
    **/
    //1.过滤脏数据
    var tuples=listFarm.filter(x => x.split("\t").length==6)
    //2.列裁剪[省份，农产品]
      .map(x=>(x.split("\t")(4),x.split("\t")(0)))
    //3.去重
      .distinct
    //4.按照省份分组
      .groupBy({
        case (x,y) =>x
      })
    //5.统计每个省份农产品的种类数
      .map(x=>(x._1,x._2.size))
    //6.倒叙排序，取前3
      .toList.sortBy(x=>x._2).reverse.take(3)
    //6.展示
    tuples
  }

  /**
   * 统计每个省份农产品种类数最多的三个农贸市场
   * @param listFarm
   * @return
   */
  def work3(listFarm:List[String])={
    /**
    val stringToTuples = listFarm.map(x => {
      val strings = x.split("\t")
      (strings(0), (strings(strings.length - 3), strings(strings.length - 2)))
    }).groupBy(_._2).map(x => (x._1, x._2.size)).toList.
      sortBy(_._2)(new Ordering[Int] {
        override def compare(x: Int, y: Int): Int = {
          if (x > y) -1
          else if (x == y) 0
          else 1
        }
      }).groupBy(_._1._2).filter(x=>x._1!="")
      .map(x=>(x._1,x._2.map(_._1._1))).map(x=> {
      if (x._2.size <= 3) {
        (x._1, x._2)
      } else {
        (x._1, x._2.slice(0,3))
      }
    })
    stringToTuples
    */

    //1.过滤(留下切分之后的list的长度=6的)
    var tuples=listFarm.filter(x=>x.split("\t").length==6)
    //2.列裁剪【省份、农贸市场、菜名】
      .map(x=>{
        val strings = x.split("\t")
        (strings(4),strings(3),strings(0))
      })
    //3.去重
      .distinct
    //4.按照省份、农贸市场分组
      .groupBy({
        case (province, market, vegetable) => (province,market)
      })
    //5.统计每个省份，每个农贸市场菜的种类数
      .map({
        case ((province,market),list)=>(province,market,list.size)
      })
      //List[(省份，市场，数量)]
    //6.按照省份分组
      .groupBy({
        case (province, market,num)=>province
      })
      //Map(省份->(省份，市场，数量))
    //7.对每个省份所有农贸市场的菜的种类数据排序取前三
      .toList.map(x=>{
        x._2.toList.sortBy(x=>x._3).reverse.take(3)
      })
    //8.展示
    tuples
  }

  /**
   *  统计每个区域的平均等客时间
   *哎呦，卧槽，好特么难
   */
  def work4(list:List[String])={
  /**
    val stringToInt = list.map(x => {
      val strings = x.split("\t")
      val timeStart = LocalDateTime.parse(
        strings(3), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        .atZone(ZoneId.systemDefault()).toInstant.toEpochMilli
      val timeEnd = LocalDateTime.parse(
        strings(4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        .atZone(ZoneId.systemDefault()).toInstant.toEpochMilli
      (strings(1), timeEnd - timeStart)
    }).groupBy(_._1).map(x=>(x._1,x._2.map(_._2).sum/x._2.size))
//      .map(x => (x._1, x._2(1)._2))
    stringToInt
    */
    //1.读取数据
    //2.切割，转换时间
    list.map(x=>{
      val strings = x.split("\t")
      val id = strings(0)
      val fromAdd=strings(1)
      val endAdd=strings(2)
      val format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val startTime=format.parse(strings(3)).getTime
      val endTime=format.parse(strings(4)).getTime
      (id,endAdd,startTime,endTime)
    })
    //3.按照司机分组
      .groupBy{
        case (id,endAdd,startTime,endTime)=>id
      }
      //司机Id->List((1,终点,开始时间，结束时间),(2,终点,开始时间，结束时间))
   //4.对每个司机所有数据排序，两两计算
      .toList.flatMap({
        case (id,list)=>{
          //按照结束时间排序
          val sortTime = list.sortBy(_._4)
          //将排序的数据上下两两计算
          val slidingList=sortTime.sliding(2)
          //获取窗口的第一条和第二条
          val tuples = slidingList.map(x => {
            val head = x.head
            val last = x.last
            var endTime = head._4
            var nextStart = last._3
            var region = head._2
            var timeAvg = (last._3 - head._4)
            (region, timeAvg)
          })
          tuples
        }
      })
    //5.按照等客区域分组
      .groupBy(x=>x._1)
    //6.求平均值
      .map{
        case (region,list)=>{
          val avg=list.map(_._2).sum/list.map(_._2).size.toDouble
          (region,avg)
        }
      }
    //7.结果展示
  }

  /**
   * 需求: 统计每个用户一小时内的最大登录次数
   * mysql中可以自join实现
   * @param list
   */
  def  work5(list: List[String])={
    //1.读取数据
    //2.切割数据
    val tuples = list.map(x => {
      val strings = x.split(",")
      var user = strings.head
      val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      var time = format.parse(strings.last).getTime
      (user, time)
    })

    //3.遍历左表，讲左表的每条数据和右表进行比较，统计登录次数
    //核心代码
    val list1 = tuples.map {
      case (user, time) => {
        var rList = tuples.filter {
          case (rUser, rTime) =>
            rTime - time <= 3600000 && rUser == user && rTime >= time
        }
        (user, rList.size)
      }
    }
    //4.按照用户分组，获取最大值
    list1.groupBy{
      case(user,list)=>user
    }.map{
      case (user,list)=>
        val sum =list.maxBy(_._2)
        (user,sum._2)
    }
  }


  def main(args: Array[String]): Unit = {
    val listFarmmer = Source.fromFile("datas/product.txt", "utf-8").getLines().toList
    val province = Source.fromFile("datas/allprovince.txt", "utf-8").getLines().toList
//    work1(listFarmmer,province).foreach(println(_)) //海南、贵州、云南、台湾、西藏、香港、 澳门
//      work2(listFarmmer).foreach(println(_)) //北京、 山西、江苏
//      work3(listFarmmer).foreach(println(_))

    val waitList=Source.fromFile("datas/2.txt","utf-8").getLines().toList
//    work4(waitList).foreach(println(_))

    val logFile=Source.fromFile("datas/1.txt","utf-8").getLines().toList
    work5(logFile).foreach(println(_))
  }
}
