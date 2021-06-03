package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sijue
 * @date 2021/6/2 19:36
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetail {
    private String order_detail_id;
    private String order_id;
    private String order_status;
    private String create_time;
    private String user_id;
    private String sku_id;
    private String user_gender;
    private Integer user_age;
    private String user_level;
    private Double sku_price;
    private String sku_name;
    private String dt;
    private String  es_metadata_id;
}
