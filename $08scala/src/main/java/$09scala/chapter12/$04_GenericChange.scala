package $09scala.chapter12

object $04_GenericChange {

  //非变
  class Test[T]

  //协变
  class Test2[+T]


  //协变
  class Test3[-T]
  /**
    * 协变: +T  如果泛型之间有父子关系,那么创建的对象继承了泛型的父子关系
    * 非变: T  如果泛型之间有父子关系,创建的对象没有任何关系
    * 逆变: -T 如果泛型之间有父子关系,,那么创建的对象颠倒了泛型的父子关系
    * @param args
    */
  def main(args: Array[String]): Unit = {

    var list1 = List[Person](new Person,new Person)
    var list2 = List[Student](new Student,new Student)
    //协变
    list1 = list2
    println(list1)

    //非变
    var test1 = new Test[Person]
    var test2 = new Test[Student]
    //test1 = test2
    //test2 = test1

    //协变
    var test3 = new Test2[Person]
    var test4 = new Test2[Student]

    test3 = test4
    println(test3)

    //test4 = test3

    //逆变
    var test5 = new Test3[Person]
    var test6 = new Test3[Student]

    test6 = test5
    println(test6)


  }

  class Person

  class Student extends Person
}
