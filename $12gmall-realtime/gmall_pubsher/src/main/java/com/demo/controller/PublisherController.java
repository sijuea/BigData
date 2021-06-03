package com.demo.controller;

import com.demo.entity.DAUPreHour;
import com.demo.entity.GMVPreHour;
import com.demo.service.PublisherService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sijue
 * @date 2021/5/28 14:14
 * @describe
 */
@RestController
public class PublisherController {
    @Autowired
    PublisherService service;
    /**
     * 查询当日日活，当日新增
     * 当日日活：count(*)  where dt="今天" group by mid_id
     * 当日新增：今天的数据left join昨天的数据 and  昨天的数据==null
     *
     * @param date
     * @return [{"id":"dau","name":"当日日活","value":1200},
     * {"id":"new_mid","name":"新增设备","value":233},
     * {"id":"order_amount","name":"新增交易额","value":1000.2 }]
     */
    @RequestMapping("realtime-total")
    public JSONArray total(String date){
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObject1=new JSONObject();
        JSONObject jsonObject2=new JSONObject();

        jsonObject.put("id","dau");
        jsonObject.put("name","当日日活");
        jsonObject.put("value",service.select(date));

        jsonObject1.put("id","new_mid");
        jsonObject1.put("name","新增设备");
        jsonObject1.put("value",service.selectNew(date));
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);

        jsonObject2.put("id","order_amount");
        jsonObject2.put("name","新增交易额");
        jsonObject2.put("value",service.selectGMV(date));
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        return jsonArray;

    }

    /**
     * 分时查询数据
     * @param date
     * @param id
     * @return 一个jsonObject对象
     * id=dau
     * {"yesterday":{"11":383,"12":123,"17":88,"19":200 },
     * "today":{"12":38,"13":1233,"17":123,"19":688 }}
     * id=order_amount
     * {"yesterday":{"11":383,"12":123,"17":88,"19":200 },
     * "today":{"12":38,"13":1233,"17":123,"19":688 }}
     */
    @RequestMapping("realtime-hours")
    public JSONObject getDataByhours(String id,String date) {
        //今天的日期
        LocalDate today = LocalDate.parse(date);
        //昨天
        LocalDate yestoday = today.minusDays(1);
        JSONObject jsonObject = new JSONObject();
        if (id.equals("dau")) {
            //查今天的数据
            List<DAUPreHour> hourOfDayToday = service.getHourOfDay(today.toString());
            //查昨天的数据
            List<DAUPreHour> hourOfDayYestoday = service.getHourOfDay(yestoday.toString());
            //拼接数据格式
            jsonObject.put("yesterday", CollectionUtils.isEmpty(hourOfDayYestoday)?null:handle(hourOfDayYestoday));
            jsonObject.put("today", CollectionUtils.isEmpty(hourOfDayToday)?null:handle(hourOfDayToday));
        } else {
            //查今天的数据
            List<GMVPreHour> hourOfDayToday = service.selectGMVByHour(today.toString());
            //查昨天的数据
            List<GMVPreHour> hourOfDayYestoday = service.selectGMVByHour(yestoday.toString());
            //拼接数据格式
            List<JSONObject> list = new ArrayList<>();
            jsonObject.put("yesterday", handle1(hourOfDayYestoday));
            jsonObject.put("today", handle1(hourOfDayToday));
        }
        return jsonObject;
    }

    public JSONObject handle(List<DAUPreHour> list){
        JSONObject jsonObject=new JSONObject();
        for(DAUPreHour hour:list){
            jsonObject.put(hour.getHour(),hour.getCount());
        }
        return jsonObject;
    }

    public JSONObject handle1(List<GMVPreHour> list){
        JSONObject jsonObject=new JSONObject();
        for(GMVPreHour hour:list){
            jsonObject.put(hour.getHour(),hour.getNum());
        }
        return jsonObject;
    }

    /**
     * 查询商品详细信息
     * date=2020-08-21&=1&size=5&keyword=小米手机
     * 因为ES没有提供jdbc操作，但是有jest客户端，所以使用jest客户端 操作
     * @return
     */
    @RequestMapping("sale_detail")
    public JSONObject getSaleDetail(String  date, Integer startpage, Integer size, String keyword) throws IOException {
        return service.search(date,startpage,size,keyword);

    }




}
