package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sijue
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {



    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    private Integer id;

    private String name;

    private Integer age;

    private String op;

}