package $07hbase.base;

import java.util.Arrays;

/**
 * @author sijue
 * @date 2021/4/18 18:41
 * @describe
 */
public class TestClass {
    public static void main(String[] args) {
        String str[]={"1","11","10","2","3","22","113"};
        Arrays.sort(str);
        for(String str1:str) {
            System.out.print(str1+" ");
        }
    }
}
