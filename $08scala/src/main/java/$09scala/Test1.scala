package $09scala

import scala.io.Source

object Test1 {

  /**
    * 1、统计哪些省份没有农产品市场
    * 2、统计农产品种类数最多的三个省份
    * 3、统计每个省份农产品种类数最多的三个农贸市场
    *
    */
  def main(args: Array[String]): Unit = {

    //1、读取数据
    val allprovinces = Source.fromFile("datas/allprovince.txt","utf-8").getLines().toList
    val products = Source.fromFile("datas/product.txt","utf-8").getLines().toList

    //m1(allprovinces,products)
   // m2(products)
    m3(products)
  }
  //统计每个省份农产品种类数最多的三个农贸市场
  def m3(products:List[String])={

    //1、过滤
    products.filter(line=> line.split("\t").length==6)
    //2、列裁剪[省份、农贸市场名称、菜名]
      .map(line=>{
         val arr = line.split("\t")
         (arr(4),arr(3),arr(0))
      })
    //3、去重
      .distinct
    //4、按照省份、农贸市场分组
      .groupBy{
          case (province,market,name) => (province,market)
        }
    //Map(
    //    (广东省,A农贸市场) -> List( (广东省,A农贸市场,大白菜) ,(广东省,A农贸市场,小白菜) ,.. )
    //    (广东省,B农贸市场) -> List( (广东省,B农贸市场,上海青) ,(广东省,B农贸市场,西红柿) ,.. )
    // )
    //5、统计每个省份、每个农贸市场菜的种类数
      .map{
      case ((province,market),list) =>
        (province,market,list.size)
    }
    //List(
    //    (广东省,A农贸市场,20)
    //    (广东省,B农贸市场,15)
    //    (湖南省,C农贸市场,25) ...
    // )
    //6、按照省份分组
      .groupBy{
          case (province,market,num) => province
        }
    //Map(
    //    广东省 -> List((广东省,A农贸市场,20) , (广东省,B农贸市场,15) ,..)
    // )
    //7、对每个省份所有农贸市场的菜的种类数排序取前三
      .map{
          case (province,list) =>
              val top3 = list.toList.sortBy(_._3).reverse.take(3)
             (province,top3)
        }
    //8、结果展示
      .foreach(println(_))
  }

  //2、统计农产品种类数最多的三个省份
  def m2(products:List[String]): Unit ={

    //1、过滤脏数据
    val filterList = products.filter(line=> line.split("\t").length==6)
    //2、列裁剪[省份、农产品名称]
    val selectList = filterList.map(line=>{
      val arr = line.split("\t")
      val province = arr(4)
      val name = arr(0)

      (province,name)
    })
    //3、去重
    val distinctList = selectList.distinct
    //4、按照省份分组
    val groupedMap = distinctList.groupBy{
      case (province,name) => province
    }
    //Map[
    //   广东省 -> List( (广东省,大白菜),(广东省,上海青),(广东省,紫苏),.. )
    //   湖南省 -> List( (湖南省,鲫鱼),(湖南省,西红柿),(湖南省,茄子),.. )
    //   ...
    // ]
    //5、统计每个省份农产品的种类数
    val countList = groupedMap.map(x=>{
      //x = 广东省 -> List( (广东省,大白菜),(广东省,上海青),(广东省,紫苏),.. )
      (x._1,x._2.size)
    })
    //6、排序，获取前三
    val sortList = countList.toList.sortBy{
      case (province,num) => num
    }.reverse

    sortList.take(3).foreach(println(_))
    //7、展示
  }

  //1、统计哪些省份没有农产品市场
  def m1(allProvince:List[String],product:List[String])= {
    //1、过滤[过滤脏数据]
    val filterList = product.filter(line=>line.split("\t").length==6)
    //2、列裁剪[只要省份]
    val prductProvices = filterList.map(line=>{
      line.split("\t")(4)
    })
    //3、去重
    val distinctProvinces = prductProvices.distinct

    //4、所有省份差农产品省份
    val result = allProvince.diff(distinctProvinces)
    //5、结果展示
    result.foreach(println(_))
  }


}
