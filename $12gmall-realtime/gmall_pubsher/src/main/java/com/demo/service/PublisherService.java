package com.demo.service;

import com.demo.entity.DAU;
import com.demo.entity.DAUExample;
import com.demo.entity.DAUPreHour;
import com.demo.entity.GMVPreHour;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author sijue
 * @date 2021/5/28 14:18
 * @describe
 */

public interface PublisherService {
    public Integer select(String date);

    public Integer selectNew(String date);

    List<DAUPreHour>getHourOfDay(String Date);

    public Double selectGMV(String date);

    List<GMVPreHour> selectGMVByHour(String date);

    public JSONObject search(String  date, Integer startpage, Integer size,String keyword) throws IOException;
}
