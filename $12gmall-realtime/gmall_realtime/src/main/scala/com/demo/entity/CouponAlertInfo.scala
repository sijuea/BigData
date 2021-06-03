package com.demo.entity

/**
 * @author sijue
 * @date 2021/6/1 15:08
 * @describe
 */
case class CouponAlertInfo(mid:String,
                           uids:java.util.HashSet[String],
                           itemIds:java.util.HashSet[String],
                           events:java.util.List[String],
                           ts:Long)

