package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sijue
 * @date 2021/5/30 21:54
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DAUPreHour {
    private String hour;
    private Integer count;


}
