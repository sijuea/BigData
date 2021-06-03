package com.demo.tdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by VULCAN on 2021/5/25
 *          ①空参构造器
 *             ②为私有属性提供getter,setter
 */
@Data // 生成getter，setter,toString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Integer id;
    private String lastName;
    private String email;
    private String gender;

    public static void main(String[] args) {

        Employee employee = new Employee();

        Employee employee1 = new Employee(1, "jack", "jack@qq.com", "male");

        System.out.println(employee1);

        employee.setEmail("tom@qq.com");

        System.out.println(employee.getEmail());

    }


}
