package $09scala.chapter05

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object $03_MethodParam {

  /**
    * 方法的参数:
    *   1、默认值参数: <默认值参数一般是放在参数列表最后面>
    *         语法: def 方法名(参数名:参数类型=默认值,...):返回值类型 = {方法体}
    *   2、带名参数: <在调用方法的时候将参数值指定传递给方法的哪个参数,>
    *   3、可变参数: <当不确定参数个数的时候可以使用可变参数>
    *         注意事项:
    *             1、可变参数不能与默认值参数一起使用
    *             2、可变参数必须放在参数列表最后面
    *         java的可变参数可以直接传递数组,scala可变参数不能传递数组。
    *         scala要想将集合所有元素传递给可变参数,可以通过 集合:_*
    *         语法: def 方法名(参数名:参数类型* ):返回值类型 = {方法体}
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //带名参数
    //TODO: 在使用带名参数的时候,参数命名必须是方法的参数名
    println(add(y=100))

    println(sum(y=10,x=100,200,300))

    //val arr = Array[Int](10,20,30)
    //println(sum(arr))
    val paths = getPath(7,"/user/hive/warehouse/user_info")
    //println(paths)

    readFile(paths:_*)

    val func = add _



  }

  //默认值参数
  def add(x:Int=20,y:Int) = x+y

  //可变参数
  def sum(y:Int,x:Int*):Int = x.sum

  //  /user/hive/warehouse/user_info/20210413
  //  /user/hive/warehouse/user_info/20210412
  //  /user/hive/warehouse/user_info/20210411
  //  /user/hive/warehouse/user_info/20210410
  //  /user/hive/warehouse/user_info/20210409
  //  /user/hive/warehouse/user_info/20210408
  //  /user/hive/warehouse/user_info/20210407
  //需求: 统计前7天的用户注册数
  //1、获取前7天的目录
  def getPath(n:Int,pathPrefix:String) = {

    //获取当前时间
    val currentTime = LocalDateTime.now()


    for(i<- 1 to 7) yield{
      //获取前第i天
      val time = currentTime.plusDays(-i)
      //格式化
      val timestr = time.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

      s"${pathPrefix}/${timestr}"
    }

  }

  def readFile(paths:String*) = {
    //模拟读取数据
    for(path<- paths){
      println(path)
    }
  }

}
