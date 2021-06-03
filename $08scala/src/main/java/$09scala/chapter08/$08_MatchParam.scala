package $09scala.chapter08

object $08_MatchParam {

  def main(args: Array[String]): Unit = {

    val t = ("zhangsan",20)

    println(t._1)

    val (name,age) = ("zhangsan",20)
    println(name)
    println(age)


    val List(x,_*) = List(1)

   /* List(1) match{
      case List(x,y,z) => ..
    }*/
    println(x)

    val Array(a,y,z) = Array(10,20,30)
    println(a,y)


    val map = Map( "aa"->1,"vv"->2)

    for( (name,age) <- map){
      println(name)
      println(age)
    }

  }
}
