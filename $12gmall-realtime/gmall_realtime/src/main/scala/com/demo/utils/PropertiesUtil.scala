package com.demo.utils

import java.io.InputStreamReader
import java.util.Properties

/**
 * @author sijue
 * @date 2021/5/31 18:36
 * @describe 读取配置文件
 */
object PropertiesUtil {

  def load(propertieName:String): Properties ={
    val prop=new Properties()
    prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(propertieName) , "UTF-8"))
    prop
  }


}
