package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author sijue
 * @date 2021/6/2 19:40
 * @describe
 */
@Data
@AllArgsConstructor
public class Stat {
    String title;
    List<Option> options;
}

