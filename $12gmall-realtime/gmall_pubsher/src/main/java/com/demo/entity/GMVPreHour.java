package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sijue
 * @date 2021/5/31 23:08
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GMVPreHour {
    String hour;
    Double num;
}
