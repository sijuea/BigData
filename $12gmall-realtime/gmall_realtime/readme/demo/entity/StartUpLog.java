package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sijue
 * @date 2021/5/28 10:07
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartUpLog implements Serializable {
    private String mid;
    private String uid;
    private String appid;
    private String area;
    private String os;
    private String ch;
    private String type;
    private String vs;
    /*数据访问的那一天*/
    private String logDate;
    /*数据访问的某天的某个小时天*/
    private String logHour;
    private Long ts;



}
