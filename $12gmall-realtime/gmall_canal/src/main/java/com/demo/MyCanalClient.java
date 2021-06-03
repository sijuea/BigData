package com.demo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.demo.constants.CommonConstants;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author sijue
 * @date 2021/5/30 23:32
 * @describe
 */
public class MyCanalClient {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("hadoop102",
                11111),
                "example", "", "");
        //连接server
        connector.connect();
        //订阅
        connector.subscribe("gmall_realtime.*");
        //取数
        while (true){
            Message message = connector.get(100);
            //当前服务器端可能没有数据，之后再拉取
            if(message.getId()==-1){
                System.out.println("没有数据歇歇");
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }else{
            //拉取到数据进行解析
            List<CanalEntry.Entry> entries = message.getEntries();
            for (CanalEntry.Entry entry : entries) {
                if (entry.getEntryType().equals(CanalEntry.EntryType.ROWDATA)) {
                    //表示当前是增删改查的数据
                    //获取表名
                    String tableName = entry.getHeader().getTableName();
                    //获取storeValue
                    ByteString storeValue = entry.getStoreValue();
                    //解析value
                    parseValue(tableName, storeValue);
                }
            }
            }


        }

    }

    private static void parseValue(String tableName,ByteString storeValue) throws InvalidProtocolBufferException {
        CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(storeValue);
        //获取order_info 的insert语句插入数据的每行的列名和写入后的值
        if (("order_info").equals(tableName) && rowChange.getEventType().equals(CanalEntry.EventType.INSERT)) {
            writeToKafka(tableName,rowChange);
        } else if (("order_detail").equals(tableName) && rowChange.getEventType().equals(CanalEntry.EventType.INSERT)) {
            //order_detail 只有事实表，所以也是新增的操作
            writeToKafka(tableName,rowChange);
        }else if(("user_info").equals(tableName)
                && (rowChange.getEventType().equals(CanalEntry.EventType.INSERT)
                    ||rowChange.getEventType().equals(CanalEntry.EventType.UPDATE))){
            //user_info的数据不只新增还有可能修改
            writeToKafka(tableName,rowChange);

        }
    }

    public static void writeToKafka(String tableName,CanalEntry.RowChange rowChange){
        //获取sql语句影响的多行
        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
        //遍历
        for (CanalEntry.RowData rowData : rowDatasList) {
            //写入后所有列的集合
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            //每一列
            JSONObject jsonObject=new JSONObject();
            for (CanalEntry.Column column : afterColumnsList) {
                jsonObject.put(column.getName(),column.getValue());
            }
            //写入kafka
            if(tableName.equals("order_info")) {
                MyProducer.writeToKafka(jsonObject.toJSONString(), CommonConstants.KAFKA_ORDER_INFO);
            }else if(tableName.equals("order_detail")){
                MyProducer.writeToKafka(jsonObject.toJSONString(), CommonConstants.KAFKA_ORDER_DETAIL);
            }else  if(tableName.equals("user_info")){
                MyProducer.writeToKafka(jsonObject.toJSONString(), CommonConstants.KAFKA_USER_INFO);
            }
        }
        }
}
