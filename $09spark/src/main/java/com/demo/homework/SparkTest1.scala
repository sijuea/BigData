package com.demo.homework

import java.text.SimpleDateFormat
import java.util.UUID

/**
 * @author sijue
 * @date 2021/4/21 18:51
 * @describe
 */
object SparkTest1 {
  def main(args: Array[String]): Unit = {
    val list = List[(String, String, String)](
      ("1001", "2020-09-10 10:21:21", "home.html"),
      ("1001", "2020-09-10 10:28:10", "good_list.html"),
      ("1001", "2020-09-10 10:35:05", "good_detail.html"),
      ("1001", "2020-09-10 10:42:55", "cart.html"),
      ("1001", "2020-09-10 11:35:21", "home.html"),
      ("1001", "2020-09-10 11:36:10", "cart.html"),
      ("1001", "2020-09-10 11:38:12", "trade.html"),
      ("1001", "2020-09-10 11:40:00", "payment.html"),
      ("1002", "2020-09-10 09:40:00", "home.html"),
      ("1002", "2020-09-10 09:41:00", "mine.html"),
      ("1002", "2020-09-10 09:42:00", "favor.html"),
      ("1003", "2020-09-10 13:10:00", "home.html"),
      ("1003", "2020-09-10 13:15:00", "search.html")
    )
    userTrace(list).foreach(println(_))

  }

  case class Person(var id: String, var time: Long, var uuid: UUID = UUID.randomUUID(),
                    var pages: String, var rank: Int)

  /**
   * 需求： 分析每个用户每次会话的行为轨迹[半小时之内]
   *
   * @param list
   */
  def userTrace(list: List[(String, String, String)]) = {
    //将元组转换为样例类Perosn
    val persons = list.map(x => {
      var id = x._1
      val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      var time = format.parse(x._2).getTime
      var pages = x._3
      Person(id = id, time = time, pages = pages, rank = 1)
    })
    //按照用户分组
    persons.groupBy {
      case p: Person => p.id
    }.flatMap(x => {
      var listMsg = x._2.sortBy(x => x.time)
      //建立窗口，大小=2，
      // 返回的数据是List(List(person1,person2),List(person2,person2))
      // 比较两条数据的相差是否小于半小时
      //这里foreach不能用map，因为迭代器的map不会做任何操作 ，只会返回一个新的迭代器
      listMsg.sliding(2).foreach(x => {
        var head = x.head
        var last = x.last
        //比较两条数据的时间戳相差，
        //之前的rank值默认都是1
        // 如果小于半个小时，就更新第二条数据的sessionId以及rank值+1
        if (last.time - head.time <= 30 * 60 * 1000) {
          last.uuid = head.uuid
          last.rank = head.rank + 1
        }
      })
      listMsg
    })
  }
}
