package com.demo.$02mapreduce.compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author sijue
 * @date 2021/3/16 18:06
 * @describe
 */
public class CompressTest {
    /**
     * 压缩文件1
     */
    @Test
    public void compress() throws Exception {
        //输入文件流
        FileInputStream fis=new FileInputStream("D:\\1\\input\\order\\order.txt");
        //输出压缩流
        CompressionCodec gzipCodec =
                ReflectionUtils.newInstance(GzipCodec.class, new Configuration());
        CompressionOutputStream os = gzipCodec.createOutputStream(new FileOutputStream("D:\\1\\input\\order\\order.txt"+gzipCodec.getDefaultExtension()));
        //流的对拷
        IOUtils.copyBytes(fis,os,1024,true);
    }



    /**
     * 解压
     */
    @Test
    public void uncompress() throws Exception {
        CompressionCodec gzipCodec =
                ReflectionUtils.newInstance(GzipCodec.class, new Configuration());
        CompressionInputStream is = gzipCodec.createInputStream(new FileInputStream("D:\\1\\input\\order\\order.txt.gz"));

        FileOutputStream fos=new FileOutputStream("D:\\1\\input\\order\\1\\uncom.txt");
        IOUtils.copyBytes(is,fos,1024,true);
    }


    /**
     * 解压文件2
     */
    @Test
    public void uncompress2() throws Exception {
        //输入文件流
        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());
        CompressionCodec codec = factory.getCodec(new Path("D:\\1\\input\\order\\order.txt.gz"));
        if(codec==null){
            System.out.println("the file error");
            return;
        }
        CompressionInputStream is = codec.createInputStream(new FileInputStream(new File("D:\\1\\input\\order\\order.txt.gz")));
        FileOutputStream os = new FileOutputStream("D:\\1\\input\\order\\22\\order.txt");
        //流的对拷
        IOUtils.copyBytes(is,os,1024,true);
    }

}
