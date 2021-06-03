package com.demo.$02mapreduce.demo;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sijue
 * @date 2021/3/9 10:55
 * @describe
 */

public class UsuallyTest {
    @Test
    public void testRegex(){
        String regex="^136|137|138|139(.*)";
        Pattern r = Pattern.compile(regex);
        String str="1398585";
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }
}
