package $09scala.test.function

/**
 * @author sijue
 * @date 2021/4/14 20:17
 * @describe
 */
object OtherTest {
  /**
   * 该函数输出一个集合，对应给定区间内给定的给定函数的输入和输出，R比如values(x=>x*x,-5,5)
   * * 应该产出一个对偶集合（-5，25），（-4，16），（-3，9）...（5，25）
   */
    def test1(x:Int,func:Int=>Int): Unit ={
      func(x)
    }
  val func1=(x:Int)=>{ x*x}
  def main(args: Array[String]): Unit = {
    //test1
    println(test1(-5, func1))
  }


}
