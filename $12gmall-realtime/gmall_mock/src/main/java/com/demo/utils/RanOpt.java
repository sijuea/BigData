package com.demo.utils;

/**
 * @author sijue
 * @date 2021/5/26 10:54
 * @describe
 */
public class RanOpt<T> {
    T value ; //表示权重
    int weight;

    public RanOpt ( T value, int weight ){
        this.value=value ;
        this.weight=weight;
    }

    public T getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

}
