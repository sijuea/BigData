package $09scala.chapter07

object $15_CollectionHightFunction {

  def main(args: Array[String]): Unit = {

    //根据指定规则过滤
    //过滤 filter( func: 集合元素类型=> Boolean )
    //filter里面的函数是针对集合每个元素进操作
    //filter保留的是函数返回值为true的数据
    val list = List[Int](10,2,5,3,6,8,7)
    //需求： 只要偶数
    val list2 = list.filter(x=> x%2==0)
    println(list2)

    //数据转换[数据的值转换、类型的转换]
    //map(func: 集合元素类型=> B )
    //map里面的函数是针对集合每个元素操作,每个元素操作完成之后返回一个结果
    //val B = A.map，此时A集合长度=B集合长度
    //map用于一对一场景
    val list3 = List("hello","spark","scala","hadoop")
    //需求: 将集合的每个元素 转换成 元素的长度
    val list4 = list3.map(x=> x.length)
    println(list4)

    //压平:  flatten
    //flatten适用于集合套集合的数据格式
    //flatten是将第二层集合压掉
    //flatten用于一对多
    //val B = A.flatten，此时A集合长度<=B集合长度
    val list5 = List[List[String]](
      List("aa","bb","ccc"),
      List("dd","ee"),
      List("tt","rr")
    )

    val list6 = list5.flatten
    println(list6)

    val list7 = List[List[List[String]]](
      List(
        List("aa","cc"),
        List("dd","ff")
      ),
      List(
        List("tt","pp")
      )
    )

    println(list7.flatten)

    val list8 = List[String]("hello","spark")
    println(list8.flatten)

    //flatMap=map+flatten
    // flatMap(func: 集合元素类型=> 集合 )
    // flatMap里面的函数也是针对每个元素操作
    // flatMap的应用于一对多的场景
    // flatMap与flatten的区别:
    //      flatten不对数据做转换,只是单纯压平
    //      flatMap是先对数据进行转换之后再压平
    val list9 = List("hello spark java","scala flume")
    //List(hello,spark,java,scala,flume)
    val list10 = list9.map(x=>x.split(" "))
    val list11 = list10.flatten
    println(list11)

    println(list9.flatMap(x => x.split(" ")))

    //foreach: 对里面每个元素处理,不需要返回结果
    //foreach(func: 元素类型=>U)
    //foreach里面的函数也是针对集合每个元素操作
    //foreach与map的区别: map有返回值，foreach没有
    list2.foreach(x=>{
      println(x)
      //将数据保存到mysql
    })

    //根据指定字段分组: groupBy(func: 集合元素=>K )
    //后续会根据groupBy函数的返回值进行分组
    //groupBy里面的函数也是针对集合每个元素操作
    //groupBy的结果是Map,Map里面的key是函数的返回值,value是key在原集合中对应所有的元素
    val list12 = List( "zhangsna"->"nan","lisi"->"nv","wangwu"->"nan")

    //list12.groupBy(x=> x._2 )
    val list13 = list12.groupBy(_._2 )
    println(list13)

    //聚合
    // reduce(func: (集合元素类型,集合元素类型)=>集合元素类型)
    // reduce按照指定的规则对集合进行聚合,聚合结果只有一个
    //  reduce是从左向右计算
    // (agg,curr)=>agg+curr
    //    第一次计算的时候 agg=集合第一个元素  curr: 第二个元素
    //    第N次计算的时候 agg=上一次计算结果  curr: 第N+1个元素
    val result = list.reduce( (agg,curr)=>{
      agg-curr
    } )
    println(result)
    // reduceRight(func: (集合元素类型,集合元素类型)=>集合元素类型)
    //  reduceRight是从右向左计算
    println(list.reduceRight((curr, agg) => {
      println(s"agg=${agg} curr=${curr}")
      agg - curr
    }))

    // fold(默认值)(func:  (集合元素类型,集合元素类型)=>集合元素类型 )
    // fold从左向右聚合
    // fold与reduce唯一的区别: fold在第一次计算的时候agg有默认值, reduce是将集合第一个元素作为agg的初始值
    list.fold(100)( (agg,curr)=> {
      println(s"fold agg=${agg} curr=${curr}")
      agg+curr
    })

    // foldRight(默认值)(func:  (集合元素类型,集合元素类型)=>集合元素类型 )
    // foldRight是从右向左计算
    // foldRight与reduceRight唯一的区别: foldRight在第一次计算的时候agg有默认值, reduceRight是将集合最后一个元素作为agg的初始值

    println(list.foldRight(100)((curr, agg) => {
      println(s"foldRight agg=${agg} curr=${curr}")
      agg - curr
    }))

  }
}
