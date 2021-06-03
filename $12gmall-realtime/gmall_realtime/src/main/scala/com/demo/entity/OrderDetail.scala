package com.demo.entity

/**
 * @author sijue
 * @date 2021/6/2 10:11
 * @describe
 */
case class OrderDetail(id:String,
                       order_id: String,
                       sku_name: String,
                       sku_id: String,
                       order_price: String,
                       img_url: String,
                       sku_num: String)
