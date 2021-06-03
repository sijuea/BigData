package $09scala.chapter02

object $04_String {

  /**
    * java创建一个字符串:
    *     1、通过"":  String name = "xxx"
    *     2、通过new: String name = new String("..")
    *     3、拼接: String name = "hello" + "aaa"
    *     4、通过一些方法: toString、subString
    * scala中创建字符串:
    *      1、通过""
    *      2、通过new:
    *      3、拼接
    *         1、通过+拼接: "hello" + "aaa"
    *         2、通过插值表达式: s"${变量/表达式}..."
    *      4、通过一些方法: format
    *      5、三引号:  """ .. """ [三引号一般在后续sparksql中写sql语句使用]
    */
  def main(args: Array[String]): Unit = {

    //1、通过""
    val name:String = "zhangsan"

    //2、通过new:
    val name2:String = new String("lisi")

    //3、拼接
    //3.1、通过+拼接
    val msg:String = name + " " + name2
    println(msg)

    //3.2、插值表达式
    val msg2 = s"${name} ${name2}"
    println(msg2)

    //4、通过一些方法
    val msg3 = 1.toString
    println(msg3)

    val host = "hadoop102"
    val port = "9870"
    //%s代表字符串的占位符
    //%d代表整数的占位符
    //%f代表浮点型的占位符
    val url = "http://%s:%s/user/hive/warehouse".format(host,port)
    println(url)

   /* val sql = "select" +
      "id," +
      "name," +
      "age" +
      " from person a left join student b" +
      " on a.id=b.id"*/

    val sql =
      """
        |select
        | id,
        | name,
        | age from person a left join student b
        |  on a.id=b.id
      """.stripMargin
    println(sql)
  }
}
