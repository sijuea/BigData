package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sijue
 * @date 2021/5/31 11:41
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private String id ;
    private String province_id ;
    private String consignee ;
    private String order_comment ;
    private String  consignee_tel ;
    private String order_status ;
    private String payment_way ;
    private String user_id ;
    private String img_url ;
    private Double total_amount ;
    private String expire_time ;
    private String delivery_address ;
    private String create_time ;
    private String operate_time ;
    private String tracking_no ;
    private String parent_order_id ;
    private String out_trade_no ;
    private String trade_body ;
    private String  create_date ;
    private String  create_hour ;

}
