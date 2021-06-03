package com.demo.tdemo.day01

import java.text.SimpleDateFormat
import java.util.UUID

case class UserAnalysis(userid:String, time:Long, page:String, var sessionId:String = UUID.randomUUID().toString, var step:Int=1)
object Test {
  /**
    * 需求： 分析每个用户每次会话的行为轨迹
    */
  def main(args: Array[String]): Unit = {

    val list = List[(String,String,String)](
      ("1001","2020-09-10 10:21:21","home.html"),
      ("1001","2020-09-10 10:28:10","good_list.html"),
      ("1001","2020-09-10 10:35:05","good_detail.html"),
      ("1001","2020-09-10 10:42:55","cart.html"),
      ("1001","2020-09-10 11:35:21","home.html"),
      ("1001","2020-09-10 11:36:10","cart.html"),
      ("1001","2020-09-10 11:38:12","trade.html"),
      ("1001","2020-09-10 11:40:00","payment.html"),
      ("1002","2020-09-10 09:40:00","home.html"),
      ("1002","2020-09-10 09:41:00","mine.html"),
      ("1002","2020-09-10 09:42:00","favor.html"),
      ("1003","2020-09-10 13:10:00","home.html"),
      ("1003","2020-09-10 13:15:00","search.html")
    )

    //1、转换数据[日期字符串转成时间戳],将其转成样例类
    val mapList = list.map{
      case (userid,timestr,page) =>
        val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = formatter.parse(timestr).getTime
        UserAnalysis(userid,time,page)
    }
    //mapList.foreach(println(_))
    //UserAnalysis(1001,1599704481000,home.html,92bc46b9-c285-4942-9a2a-ad804440a8bb,1)
    //UserAnalysis(1001,1599704890000,good_list.html,1306bc50-d3c9-49d6-855a-609fe663a50a,1)
    //UserAnalysis(1001,1599705305000,good_detail.html,de245070-0101-44c8-a393-b16f9fdfe806,1)
    //UserAnalysis(1001,1599705775000,cart.html,48a63637-dc8c-41db-9470-96dc22a2f2d1,1)
    //UserAnalysis(1001,1599708921000,home.html,8eb407a2-0039-4e82-b013-1ccd0742a717,1)
    //UserAnalysis(1001,1599708970000,cart.html,294eddc0-b2ef-497a-bbbf-36f0146db4ce,1)
    //UserAnalysis(1001,1599709092000,trade.html,6dabf291-ca26-4284-afc7-472a37e72d58,1)
    //UserAnalysis(1001,1599709200000,payment.html,c89d2606-a89c-43d9-8fb8-9b4ca81309e4,1)
    //UserAnalysis(1002,1599702000000,home.html,5ca07e10-b2e2-44e1-b093-19a2550f2f48,1)
    //UserAnalysis(1002,1599702060000,mine.html,b6ce168e-2825-46a5-8c77-b99d1786406a,1)
    //UserAnalysis(1002,1599702120000,favor.html,598e4682-a214-4dc4-9291-481db9efa2f7,1)
    //UserAnalysis(1003,1599714600000,home.html,dbfe6876-55f2-471d-88f5-babbb03cd3a8,1)
    //UserAnalysis(1003,1599714900000,search.html,86e6b437-b8aa-46d8-adfb-549aaaff5b23,1)
    //2、按照用户分组
    val groupedMap = mapList.groupBy(_.userid)
    //Map(
    //    1001 -> List(
    //            UserAnalysis(1001,1599704481000,home.html,92bc46b9-c285-4942-9a2a-ad804440a8bb,1)
    //            UserAnalysis(1001,1599704890000,good_list.html,1306bc50-d3c9-49d6-855a-609fe663a50a,1)
    //            UserAnalysis(1001,1599705305000,good_detail.html,de245070-0101-44c8-a393-b16f9fdfe806,1)
    //            UserAnalysis(1001,1599705775000,cart.html,48a63637-dc8c-41db-9470-96dc22a2f2d1,1)
    //            UserAnalysis(1001,1599708921000,home.html,8eb407a2-0039-4e82-b013-1ccd0742a717,1)
    //            UserAnalysis(1001,1599708970000,cart.html,294eddc0-b2ef-497a-bbbf-36f0146db4ce,1)
    //            UserAnalysis(1001,1599709092000,trade.html,6dabf291-ca26-4284-afc7-472a37e72d58,1)
    //            UserAnalysis(1001,1599709200000,payment.html,c89d2606-a89c-43d9-8fb8-9b4ca81309e4,1)
    //        )
    //    1002 -> ..
    //    1003 -> ..
    // )

    //3、对每个用户的数据排序，滑窗
    val result = groupedMap.flatMap(x=>{
      //x =  1001 -> List(
      //                UserAnalysis(1001,1599704481000,home.html,92bc46b9-c285-4942-9a2a-ad804440a8bb,1)
      //                UserAnalysis(1001,1599704890000,good_list.html,1306bc50-d3c9-49d6-855a-609fe663a50a,1)
      //                UserAnalysis(1001,1599705305000,good_detail.html,de245070-0101-44c8-a393-b16f9fdfe806,1)
      //                UserAnalysis(1001,1599705775000,cart.html,48a63637-dc8c-41db-9470-96dc22a2f2d1,1)
      //                UserAnalysis(1001,1599708921000,home.html,8eb407a2-0039-4e82-b013-1ccd0742a717,1)
      //                UserAnalysis(1001,1599708970000,cart.html,294eddc0-b2ef-497a-bbbf-36f0146db4ce,1)
      //                UserAnalysis(1001,1599709092000,trade.html,6dabf291-ca26-4284-afc7-472a37e72d58,1)
      //                UserAnalysis(1001,1599709200000,payment.html,c89d2606-a89c-43d9-8fb8-9b4ca81309e4,1)
      //            )
      val sortedList = x._2.sortBy(y=>y.time)
      val slidingList = sortedList.sliding(2)
      //[
      //    List( UserAnalysis(1001,1599704481000,home.html,92bc46b9-c285-4942-9a2a-ad804440a8bb,1) ,UserAnalysis(1001,1599704890000,good_list.html,1306bc50-d3c9-49d6-855a-609fe663a50a,1))
      //    List( UserAnalysis(1001,1599704890000,good_list.html,1306bc50-d3c9-49d6-855a-609fe663a50a,1) , UserAnalysis(1001,1599705305000,good_detail.html,de245070-0101-44c8-a393-b16f9fdfe806,1))
      //    List( ... )
      // ]
      slidingList.foreach(z=>{
        val first = z.head
        val last = z.last
        //4、两两对比,修改sessionid,step
        if(last.time-first.time<=30*60*1000){
          last.sessionId = first.sessionId
          last.step = first.step+1
        }
      })

      x._2

    })

    //5、结果展示
    result.foreach(println(_))

  }
}
