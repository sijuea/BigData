package $09scala.chapter12

object $01_GenericDefined {

  /**
    * 泛型方法:   def 方法名[T,U,..](参数名:T,..):U = {...}
    */
  def main(args: Array[String]): Unit = {

    printArray[Int,Unit](Array[Int](1,2,3,4,5))
    //printArray[Any,Unit](Array[Any](1,2,3,4,5))
    printArray[String,Unit](Array("aa","cc","tt"))
  }

  def printArray[T,U](arr:Array[T]) = {
    arr.foreach(println(_))
  }
}
