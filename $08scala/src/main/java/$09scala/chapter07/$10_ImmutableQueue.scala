package $09scala.chapter07

import scala.collection.immutable.Queue

object $10_ImmutableQueue {

  /**
    * 队列的特性: 先进先出
    * 不可变队列创建方式: Queue[元素类型](初始元素，初始元素，...)
    *
    */
  def main(args: Array[String]): Unit = {

    val q1 = Queue[Int](10,3,5,6,2)
    println(q1)

    //添加元素
    val q2 = q1.+:(20)

    val q3 = q1.:+(20)

    println(q2)
    println(q3)

    val q4 = q1.++( List(30,40,50) )
    val q5 = q1.++:( List(30,40,50) )
    println(q4)
    println(q5)

    val q6 = q1.enqueue(100)
    println(q6)
    println(q1)

    //删除元素
    val dequeue = q1.dequeue
    println(dequeue)

    //获取元素
    println(q1(0))

    //修改元素
    //q1(0)=20
    val q11 = q1.updated(0,20)
    println(q11)
  }
}
