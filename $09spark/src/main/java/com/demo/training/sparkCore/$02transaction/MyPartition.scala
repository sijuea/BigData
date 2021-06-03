package com.demo.training.sparkCore.$02transaction

import org.apache.spark.Partitioner

/**
 * @author sijue
 * @date 2021/4/28 19:40
 * @describe
 */
//传入分区数
class MyPartition(num:Int) extends Partitioner{
  override def numPartitions: Int = num

  override def getPartition(key: Any): Int = {
    key match {
      case "hello" =>0
      case "java" =>1
      case _=>2
    }
  }
}
