package $09scala

import java.text.SimpleDateFormat

import scala.io.Source

object Test2 {

  //需求: 统计每个用户一小时内的最大登录次数
  //select t.user_id,max(cn) from(
  //  select a.user_id,a.login_time,count(1) cn
  //	  from user_info a inner join user_info b
  //	  on a.user_id = b.user_id
  //	  and b.login_time-a.login_time<=3600s
  //	  and b.login_time>=a.login_time
  //   group by a.user_id,a.login_time
  //) t
  //group by t.user_id
  def main(args: Array[String]): Unit = {

    //1、读取数据
    val datas = Source.fromFile("datas/1.txt").getLines().toList

    //2、切割数据
    val selectList = datas.map(line=> {
      val arr = line.split(",")
      val timeStr = arr.last
      val formtter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val loginTime = formtter.parse(timeStr).getTime
      (arr.head,loginTime)
    })

    //2、遍历左表,将左表每条数据与右边进行对比，统计登录次数
    selectList.map{
      case (userId,loginTime) =>
        val rList = selectList.filter{
          case (rUserid,rLogionTime) => userId==rUserid && rLogionTime-loginTime<=3600000 && rLogionTime>=loginTime
        }
        (userId,rList.size)
    }
    //3、按照用户分组,获取最大值
      .groupBy{
      case (userid,num) => userid
    }
    //Map(
    //    a->List((a,5),(a,6),(a,5))
    //    ..
    // )
      .map{
      case (userid,list)=>
        val t1 = list.maxBy(_._2)
        (userid,t1._2 )
    }
    //4、结果展示
      .foreach(println(_))
  }
}


