package com.demo.entity;

import io.searchbox.action.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sijue
 * @date 2021/6/1 9:40
 * @describe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee  {
    private String empid;
    private Integer age;
    private Integer balance;
    private String name;
    private String gender;
    private String hobby;
}
