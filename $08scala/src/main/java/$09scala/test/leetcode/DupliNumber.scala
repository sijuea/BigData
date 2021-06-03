package $09scala.test.leetcode

import java.util

import scala.collection.mutable
import scala.util.control.Breaks.{break, breakable}

/**
 * @author sijue
 * @date 2021/4/14 19:59
 * @describe
 */
 object DupliNumber {
  def findRepeatNumber(nums: Array[Int]): Int = {
    val map=new util.HashMap[Int,Int]()
    var res=0
    breakable({
      for (num <- nums) {
        if (map.containsKey(num)) {
          if (map.get(num) + 1 >= 2) {
            res = num
            break()
          }
        } else {
          val sum = 1;
          map.put(num, sum)
        }
      }
    })
    res
  }
  def main(args: Array[String]): Unit = {
    var arr=Array(2, 3, 1, 0, 2, 5, 3)
    println(findRepeatNumber(arr))
  }
}
