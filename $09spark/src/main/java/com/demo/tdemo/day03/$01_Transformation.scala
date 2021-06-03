package com.demo.tdemo.day03

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test

import scala.collection.mutable.ListBuffer
import scala.util.Try

class $01_Transformation {

  val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("test"))

  /**
    * map(func: RDD元素类型=>B) : 映射,用于数据转换[数据值与类型的转换]
    *   map里面的函数针对的RDD每个元素进行操作
    *   map生成的新的RDD的元素的个数 = 原来的RDD的元素个数
    */
  @Test
  def map(): Unit = {

    val rdd = sc.parallelize(List("hadoop","spark","hadoop","scala","flume"))

    println(s"${Thread.currentThread().getName}")
    val rdd2 = rdd.map(x=> {
      println(s"${Thread.currentThread().getName} --- ${x}")
      x.length
    })

    println(rdd2.collect().toList)

  }

  /**
    * mapParttions(func: 迭代器[RDD元素类型]=>迭代器[B]): 用于数据转换
    *   mapPartitions里面的函数是一个分区调用一次
    *
    *   算子里面的函数是在executor中执行的,算子外面的代码是在Driver中执行的
    *
    * mapPartitions与map的区别:
    *     1、针对的对象不同
    *         map里面的函数是针对RDD每个元素
    *         mapPartitions里面的函数是针对RDD一个分区的所有元素
    *     2、返回的结果不太一样
    *         map里面的函数是一个元素返回一个结果,所以map生成的新RDD的元素个数 = 原来的RDD的元素个数
    *         mapPartitions里面的函数是传入一个分区的所有数据的迭代器,要求返回一个新的迭代器,如果有对传入的迭代器做一些比如过滤之类的操作,生成的新的RDD的元素的个数可能与原来的RDD的元素个数不相同
    *     3、RDD的元素的内存释放的实际不一样
    *         map里面的函数是针对每个元素操作,所以RDD一个元素操作完成之后,所以该元素所占用的内存可以直接释放
    *         mapPartitions里面的函数是针对一个分区的所有数据的迭代器操作,必须等到迭代器操作完成才能释放对象。所以mapParttions有可能出现内存溢出,出现内存溢出可以用map代替【完成比完美要重要】
    */
  @Test
  def mapPartitions():Unit = {

    val rdd = sc.parallelize(List("hadoop","spark","hadoop","scala","flume","hadoop","spark","hadoop","scala","flume"))

    val result = rdd.mapPartitions(it=>{
      //println(s"${it.toList}")
      it.map(_.length)
    }).collect()

    println(result.toList)


    //用户id
    val rdd3 = sc.parallelize(List(10,3,6,2,7,60))
    //需求:根据用户id从mysql查询用户的详细信息[(id,name,age,address)]
    // 以下在map中查询mysql,每条数据都会创建连接与销毁连接,性能低



    val rdd4 = rdd3.map(id=>{

      var name:String = ""
      var address:String = ""
      var sex:String = ""
      var connnection:Connection = null
      var statement:PreparedStatement = null
      try{

        connnection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123")
        statement = connnection.prepareStatement("select * from person where id=?")
        statement.setInt(1,id)
        val set = statement.executeQuery()
        while(set.next()){
          name = set.getString("name")
          sex = set.getString("sex")
          address = set.getString("address")
        }
        (id,name,address,sex)
      }catch {
        case e:Exception =>  (id,name,address,sex)
      }finally {
        if(statement!=null)
          statement.close()
        if(connnection!=null)
          connnection.close()
      }

    })

    //优化:
    rdd3.mapPartitions(it=>{
      var connection:Connection = null
      var statement:PreparedStatement = null
      val list = ListBuffer[(Int,String,String,String)]()
      try{
        connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/test","root","root123")
        statement = connection.prepareStatement("select * from person where id=?")

        it.foreach(id=>{

          statement.setInt(1,id)
          val set = statement.executeQuery()

          while(set.next()){
            val name = set.getString("name")
            val sex = set.getString("sex")
            val address = set.getString("address")
            list.+=( (id,name,sex,address) )
          }
        })
        list.toIterator
      }catch {
        case e:Exception => list.toIterator
      }finally {
        if(statement!=null)
          statement.close()
        if(connection!=null)
          connection.close()
      }


    })

    var sum = 0
    rdd3.foreach(x=> sum = sum+x)
    println(sum)


    val rdd5 = rdd3.mapPartitions(it=> {
      it.filter(_%2==0)
    })

    println(rdd5.collect().toList)

  }

  /**
    * mapPartitionsWithIndex(func: (分区号, 分区所有元素的迭代器) => 迭代器 ): 也是用于转换
    *   mapPartitionsWithIndex里面的函数也是针对每个分区操作,一个分区调用一次
    *
    * mapPartitionsWithIndex与mapPartitions的区别:
    *     mapPartitionsWithIndex里面的函数参数相比mapPartitions里面的函数参数多了一个分区号
    */
  @Test
  def mapPartitionsWithIndex(): Unit ={

    val rdd = sc.parallelize(List(10,30,5,8,20,4,2,3,7,8,10,20,30,40,50,60))

    rdd.mapPartitionsWithIndex((index,it) => {
      println(s"分区号=${index} 分区数=${it.toList}")
      it
    }).collect()

  }

  /**
    * flatMap(func: RDD元素类型 => 集合 ) = map + flatten  数据转换+压平
    *   flatMap里面的函数是针对RDD每个元素操作
    */
  @Test
  def flatMap(): Unit = {

    val rdd = sc.parallelize(List("hadoop spark hadoop","spark scala"))

    val rdd2 = rdd.flatMap(_.split(" "))

    println(rdd2.collect().toList)
  }

  /**
    * glom(): 将一个分区的所有数据用Array封装
    *    glom生成的新的RDD的元素个数 = 分区数
    */
  @Test
  def glom(): Unit ={
    val rdd = sc.parallelize(List(10,30,5,8,20,4,2,3,7,8,10,20,30,40,50,60))

    val rdd2 = rdd.glom()

    rdd2.foreach(x=>println(x.toList))
  }

  /**
    * groupBy(func: RDD元素类型 => K) : 按照指定字段分组
    *     groupBy后续是按照函数的返回值进行分组
    * MR的shuffle: [ 写入环形缓冲区-> 分区排序 ->  combiner ->溢写 -> 磁盘 ]
    *   数据->map方法->写入环形缓冲区-> 分区排序 ->  combiner ->溢写 -> 磁盘 -> reduce拉取数据-> 分组、归并-> reduce方法->写入磁盘
    *
    * spark的shuffle
    *   数据 -> map/flatMap -> 写入缓冲区 -> 分区[排序] -> [combiner] ->溢写 -> 磁盘 -> 分区拉取数据 -> 分组 -> ...
    */
  @Test
  def groupBy(): Unit = {

    val rdd = sc.parallelize(List(10,30,5,8,20,4,2,3,7,8,10,20,30,40,50,60))

    val rdd2 = rdd.groupBy(x=>x)

    println(rdd2.collect().toList)
  }

  /**
    * filter(func: RDD元素类型 => Boolean): 过滤
    *     filter里面的函数也是针对RDD每个元素操作
    *     filter保留的是函数返回值为True的数据
    */
  @Test
  def filter(): Unit = {

    val rdd = sc.parallelize(List(10,30,5,8,20,4,2,3,7,8,10,20,30,40,50,60))

    val rdd2 = rdd.filter( _ % 2 !=0 )

    println(rdd2.collect().toList)
  }

  /**
    * sample(withReplacement,fraction,seed) : 采样，<用于数据倾斜场景>
    *   withReplacement: 采样之后是否放回
    *         true代表放回,代表同一个元素可能被多次采样
    *         false代表不放回,代表同一个元素最多被采样一次<工作中一般设置的参数>
    *   fraction
    *       withReplacement=true, 代表每个元素期望被采样的次数
    *       withReplacement=false,代表每个元素被采样的概率 <工作中一般设置为0.1-0.2>
    */
  @Test
  def sample(): Unit ={

    val rdd = sc.parallelize(List("spark","spark","spark","java","spark","hello","spark","spark","spark","spark","hello"))

    val rdd2 = sc.parallelize(List(1,2,3,4,5,6,7,8,9))
    val rdd3 = rdd2.sample(true,5)
    val rdd4 = rdd2.sample(false,0)
    println(rdd4.collect().toList)
    println(rdd4.collect().toList)

    val rdd6 = rdd.sample(false,0.2)
    println(rdd6.collect().toList)
  }

  //每个省份每个农产品的平均价格
  @Test
  def test(): Unit = {

    //1、读取数据
    val rdd1 = sc.textFile("datas/product.txt")
    //2、过滤脏数据
    val rdd2 = rdd1.filter(line=> line.split("\t").length==6)
    //3、数据转换[省份、农产品字段、价格]
    val rdd3 = rdd2.map(line=>{
      val arr = line.split("\t")

      /*try{
        arr(1).toDouble
      }catch {
        case e:Exception => println(line)
      }*/
      (arr(4),arr(0),Try(arr(1).toDouble).getOrElse(0.0))
    })


    //4、按照省份、农产品分组
    val rdd4: RDD[((String, String), Iterable[(String, String, Double)])] = rdd3.filter(_._3!=0.0).groupBy{
      case (province,name,price) => (province,name)
    }
    //5、获取平均价格
    val rdd5 = rdd4.map{
      case ((province,name),it) =>
        //获取总价格
        val totalPrice = it.map(_._3).sum

        //总个数
        val num = it.size

        (province,name,totalPrice/num)
    }

    //6、结果展示
    println(rdd5.collect().toList)
  }

  /**
    * 去重
    *   distinct会产生shuffle操作
    */
  @Test
  def distinct(): Unit ={

    val rdd1 = sc.parallelize(List(1,1,2,6,4,3,10,2,6))

    val rdd2 = rdd1.distinct()

    println(rdd2.collect().toList)

    println(myDistinct(rdd1).collect().toList)

  }


  //自定义去重方法
  def myDistinct(rdd:RDD[Int]):RDD[Int] = {

    val rdd2 = rdd.groupBy(x=>x)

    rdd2.map(x=>x._1)
  }

  /**
    * coalesce: 修改分区数
    *     coalesce默认只能减少分区数,coalesce默认减少分区是没有shuffle操作的
    *     coalesce如果想要增大分区,需要将shuffle参数设置为true,此时会产生shuffle操作
    * coalesce在工作中一般搭配filter使用。
    */
  @Test
  def coalesce(): Unit ={
    val rdd1 = sc.parallelize(List(1,1,2,6,4,3,10,2,6))
    println(rdd1.partitions.length)
    val rdd2 = rdd1.coalesce(3)
    println(rdd2.partitions.length)
    val rdd3 = rdd1.coalesce(3,true)
    println(rdd3.partitions.length)

    rdd1.mapPartitionsWithIndex((index,it)=>{
      println(s"rdd1 index:${index} data=${it.toList}")
      it
    }).collect()
    rdd2.mapPartitionsWithIndex((index,it)=>{
      println(s"rdd2 index:${index} data=${it.toList}")
      it
    }).collect()
    rdd2.collect()
    rdd3.collect()

  }

  /**
    * 重分区:
    *   repartition既可以增大分区也可以减少分区,都会产生shuffle操作
    *   coalesce与repartition的区别:
    *       coalesce默认只能减少分区,而且默认减少分区的时候是没有shuffle操作的，如果需要增大分区数,需要shuffle=true
    *       repartition既可以增大分区也可以减少分区,不管是增大分区还是减少分区都会产生shuffle
    *   工作中如果是减少分区推荐使用coalesce,因为没有shuffle操作,性能更高
    *         如果是增大分区推荐使用repartition，因为写起来更简单
    */
  @Test
  def repartition(): Unit ={
    val rdd1 = sc.parallelize(List(1,1,2,6,4,3,10,2,6))
    println(rdd1.partitions.length)
    val rdd2 = rdd1.repartition(3)
    val rdd3 = rdd1.repartition(6)
    println(rdd2.partitions.length)
    println(rdd3.partitions.length)
  }

  /**
    * sortBy(func: RDD元素类型=>B): 根据指定字段排序
    *   sortBy是根据函数的返回值进行排序
    *   sortBy里面的函数是真针对RDD每个元素进行操作
    */
  @Test
  def sortBy(): Unit ={
    val rdd1 = sc.parallelize(List(1,1,2,6,4,3,10,2,6))

    val rdd2 = rdd1.sortBy(x=>x,true)

    rdd2.mapPartitionsWithIndex((index,it)=>{
      println(s"rdd2 index:${index} data=${it.toList}")
      it
    }).collect()
    println(rdd2.collect().toList)

    Thread.sleep(1000000)
  }

  /**
    * pipe(脚本路径): 调用外部脚本
    *   pipe是一个分区调用一次脚本
    *   pipe会生成一个新的RDD，该新RDD的元素是在脚本中通过echo返回
    */
  def pipe(): Unit ={
    val rdd1 = sc.parallelize(List(1,1,2,6,4,3,10,2,6))

    rdd1.pipe("/xx")

  }
}
