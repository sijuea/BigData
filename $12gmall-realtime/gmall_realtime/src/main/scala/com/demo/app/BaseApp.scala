package com.demo.app

import org.apache.spark.streaming.StreamingContext

import scala.util.control.{BreakControl, Breaks}

/**
 * @author sijue
 * @date 2021/5/31 18:35
 * @describe
 */
abstract class BaseApp {
  //声名三个抽象属性
  var appName: String;
  var internal: Int;
  var ssc: StreamingContext = null;

  //控制抽象，传入处理数据的过程函数
  def process(op: => Unit) {
    try {
      op
      ssc.start();
      ssc.awaitTermination();
    } catch {
      case ex: Exception => println(ex.getMessage)
    } finally {
      ssc.stop(true, true);
    }
  }

}
