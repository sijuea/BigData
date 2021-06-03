package com.demo.mapper;

import com.demo.entity.GMVPreHour;

import java.util.List;

/**
 * @author sijue
 * @date 2021/5/31 22:57
 * @describe
 */
public interface GMVMapper {
    Double selectGMV(String date);

    List<GMVPreHour> selectGMVByHour(String date);
}
