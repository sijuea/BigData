package $09scala.chapter02

object $06_DataType {

  /**
    * java的数据类型:
    *   1、基本数据类型: int、byte、short、long、float、double、char、boolean
    *   2、引用类型: string，class，集合，数组
    *
    * scala是完全面向对象的,scala数据类型结构:
    *   Any: 所有类的父类
    *     AnyVal: 值类型
    *       Int、Byte、Short、Long、Float、Double、Char、Boolean
    *       StringOps: 对java string的扩展
    *       Unit: 相当于java的void, 有一个实例 ()
    *     AnyRef: 引用类型
    *       String、java class、scala class、java/scala数组、集合
    *          Null: 所有引用类型的子类，有一个实例null. [null一般用于给引用类型变量赋予初始值,必须指定变量类型]
    *
    *    Nothing: 所有类型的子类,scala内部使用
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    //var age:Int = null

    //var name = null
    //name = "lisi"
    //println(age)
  }
}
