package com.demo.mapper;

import com.demo.entity.DAU;
import com.demo.entity.DAUExample;
import com.demo.entity.DAUKey;
import java.util.List;

import com.demo.entity.DAUPreHour;
import org.apache.ibatis.annotations.Param;

public interface DAUMapper {


    int selectCount(String date);

    int selectNew(String date);

    List<DAUPreHour> getHourOfDay(String date);
}