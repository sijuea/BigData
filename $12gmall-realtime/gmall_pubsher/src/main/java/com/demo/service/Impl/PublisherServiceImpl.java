package com.demo.service.Impl;

import com.demo.dao.ESDao;
import com.demo.entity.DAU;
import com.demo.entity.DAUExample;
import com.demo.entity.DAUPreHour;
import com.demo.entity.GMVPreHour;
import com.demo.mapper.DAUMapper;
import com.demo.mapper.GMVMapper;
import com.demo.service.PublisherService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author sijue
 * @date 2021/5/28 14:18
 * @describe
 */
@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    DAUMapper mapper;
    @Autowired
    GMVMapper mapper1;

    @Autowired
    ESDao esDao;
    //DAU需求
    @Override
    public Integer select(String date) {
        return mapper.selectCount(date);

    }

    @Override
    public Integer selectNew(String date) {
        return mapper.selectNew(date);
    }

    @Override
    public List<DAUPreHour> getHourOfDay(String Date) {
        return null;
    }

    //GMV需求
    @Override
    public Double selectGMV(String date) {
        return mapper1.selectGMV(date);
    }

    @Override
    public List<GMVPreHour> selectGMVByHour(String date) {
        return mapper1.selectGMVByHour(date);
    }

    @Override
    public JSONObject search(String date, Integer startpage,Integer size, String keyword) throws IOException {
        return esDao.search(date,startpage,size,keyword);
    }
}
