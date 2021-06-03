package $09scala.chapter02

object $02_ParamName {

  /**
    * java标识符定义规范: 必须是字母、数字、下划线、$,首字母不能是数字
    * scala中标识符定义规范: 必须是字母、数字、下划线、$、特殊符号,首字母不能是数字 [特殊符号在定义标识符的时候不推荐普通程序员使用,特殊符号一般是scala内部使用]
    * scala在定义变量的时候推荐还是跟java一样采用驼峰原则
    */
  def main(args: Array[String]): Unit = {

    val name = "zhangsan"

    //val 12name = "lisi"

    val $001 = "xxx"

    println($001)

    val / = 10

    //println( + )
  }
}
