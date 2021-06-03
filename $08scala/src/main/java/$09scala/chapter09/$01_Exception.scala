package $09scala.chapter09

import java.sql.{Connection, DriverManager, PreparedStatement}

import scala.util.Try

object $01_Exception {

  /**
    * java的异常处理:
    *     1、捕获异常: try{..}catch(Exception e){...}..finally{..}
    *     2、抛出异常: throw + throws XXException [在方法名后面声明方法会抛出异常]
    * scala的异常处理:
    *     1、捕获异常:
    *         1、try{..}catch{case e:Exception => ..}..finally{..} <一般只用于获取外部链接的场景>
    *         2、Try(可能出现异常的代码).getOrElse(默认值) <在处理数据的时候经常使用>
    *               Try有两个子类：
    *                   Success:  代表代码执行成功
    *                   Failture: 代表代码执行失败
    *     2、抛出异常: throw抛出异常<一般不用>
    *         scala没有throws关键字
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //println(m1(10, 2))
    //println(m1(10, 0))
    //println(m2(10, 0))
    val list = List("zhangsan 20 3000","lisi  5000","zhaoliu 15 ")
    //需求: 统计年龄、工资总和
    val result = list.map(x=>{
      val arr = x.split(" ")
      //val age = try{arr(1).toInt}catch {case e:Exception=> 0}
      val age = Try(arr(1).toInt).getOrElse(0)
      //val sal = try{arr(2).toInt}catch {case e:Exception=> 0}
      val sal = Try(arr(2).toInt).getOrElse(0)
      (age,sal)
    }).reduce( (agg,curr) => (agg._1+curr._1,agg._2+curr._2))

    println(result)

  }

  //捕获异常: try{..}catch{case e:Exception => ..}..finally{..}
  def m1(x:Int,y:Int) = {
    try{
      x/y
    }catch{
      case e:Exception => println("被除数为0")
    }
  }

  //抛出异常: throw抛出异常
  def m2(x:Int,y:Int) = {
      if(y==0) throw new Exception("被除数为0")
      x/y
  }

  def jdbcMysql() = {

    var connection:Connection = null
    var statement:PreparedStatement = null
    try{

      connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test")

      statement = connection.prepareStatement("insert into person values(?,?,?)")

      statement.setString(1,"1001")
      statement.setString(2,"zhangsan")
      statement.setInt(3,20)

      statement.executeUpdate()
    }catch {
      case e:Exception =>
    }finally {
      if(statement!=null)
        statement.close()
      if(connection!=null)
        connection.close()
    }


  }
}
