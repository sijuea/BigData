package $09scala.chapter07

object $18_ParCollection {

  /**
    * 并行集合: 利用多线程操作集合元素
    * 将普通集合转成并行集合: 集合名.par
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val list = List(10,2,6,7,10,2)

    list.foreach(x=>{
      println(s"${Thread.currentThread().getName} - ${x}")
    })
    println("*"*100)
    val parList = list.par
    parList.foreach(x=>{
      println(s"${Thread.currentThread().getName} - ${x}")
    })
  }
}
