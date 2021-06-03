package $09scala.chapter08

object $07_MatchTuple {

  def main(args: Array[String]): Unit = {

    val t1:(Any,Any,Any) = ("zhangsan",20,"shenzhen")
    //匹配元组的时候变量是几元元组，在匹配的条件中就应该匹配几元元组
    t1 match {
      case (name:String,age:Int,address:Int) => println(name,age,address,"xx")
      case (name,age,address) => println(name,age,address)
      //case (name,age) => println(name,age)
    }

    val regions = List(
      ( "宝安区",( "宝安中学", ("大数据班", ( "zhangsan",30 )) ) ),
      ( "宝安区",( "宝安中学", ("大数据班", ( "lisi",30 )) ) ),
      ( "宝安区",( "宝安中学", ("大数据班", ( "wangwu",30 )) ) )
    )

    //元组的模式匹配的应用场景
    regions.foreach( x=>{
      x match {
        case (regionName,( schoolName, (className, ( stuName,age)) )) =>
          println(stuName)
      }
    })

    regions.foreach({
      case (regionName,( schoolName, (className, ( stuName,age)) )) =>
        println(stuName)
    })
  }
}
