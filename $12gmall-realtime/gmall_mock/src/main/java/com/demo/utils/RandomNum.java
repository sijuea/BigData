package com.demo.utils;
import java.util.Random;
/**
 * @author sijue
 * @date 2021/5/26 10:56
 * @describe
 */
public class RandomNum {
    //返回随机的整数
    public static int getRandInt(int fromNum,int toNum){
        return fromNum + new Random().nextInt(toNum-fromNum+1);
    }

}

