package $09scala.chapter05

import java.util

object $11_Test3 {

  /**
    * 3、对数据中的元素按照指定规则进行分组
    * Array("zhangsan shenzhen man","lisi beijing woman","zhaoliu beijing man")
    * 规则: 按照地址进行分组
    * 结果： Map( shenzhen->List("zhangsan shenzhen man" ) , beijing->List( "lisi beijing woman","zhaoliu beijing man" ) )
    *
    */
  def main(args: Array[String]): Unit = {

    val data = Array("zhangsan shenzhen man","lisi beijing woman","zhaoliu beijing man")

    val func = (x:String)=> {
      //val arr = x.split(" ")
     x.split(" ")(1)
      //arr(1)
    }
    println(groupBy(data, func))
    //直接传递函数值
    println(groupBy(data, (x:String)=>x.split(" ")(1)))
    //省略函数类型
    println(groupBy(data, (x)=>x.split(" ")(1)))
    //省略()
    println(groupBy(data, x=>x.split(" ")(1)))
    //用_代替
    println(groupBy(data, _.split(" ")(1)))
  }

  def groupBy(data:Array[String],func: String => String) = {

    //创建一个容器,装载分组的结果数据
    val result  = new util.HashMap[String,util.List[String]]()

    //变量数组,调用规则，获取分组的字段
    for(element<- data){
      //得到分组的key
      val key = func(element)
      //如果key已经存在，代表容器中已经有该组了,需要将当前元素添加到value的list中
      if( result.containsKey(key) ){

        val list = result.get(key)
        list.add(element)
      }
      //如果key不存在,则代表没有改组,需要自己创建List，将key-List添加到容器中
      else{
        val list = new util.ArrayList[String]()
        list.add(element)
        result.put(key,list)
      }
    }

    result

  }
}
