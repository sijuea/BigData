package $09scala.chapter07

import scala.collection.mutable
object $11_MutableQueue {

  /**
    * 不可变队列创建方式: mutable.Queue[元素类型](初始元素，初始元素，...)
    * @param args
    */
  def main(args: Array[String]): Unit = {

    val q1 = mutable.Queue[Int](10,2,5,1)

    //添加数据
    val q2 = q1.+:(20)
    val q3 = q1.:+(20)
    q1.+=(30)
    q1.+=:(40)
    println(q2)
    println(q3)
    println(q1)

    val q4 = q1.++( List(100,300,200))
    val q5 = q1.++:( List(100,300,200))
    println(q4)
    println(q5)

    q1.++=(List(100,300,200))
    println(q1)

    q1.enqueue(400,500)
    println(q1)

    //删除
    println(q1.dequeue())
    println(q1)

    //获取数据
    println(q1(0))

    //修改数据
    q1(0)=100
    println(q1)

    q1.update(1,200)
    println(q1)
  }
}
