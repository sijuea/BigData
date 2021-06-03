package com.demo.dao;

import com.google.gson.JsonObject;
import net.minidev.json.JSONObject;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/6/2 18:44
 * @describe
 */
public interface ESDao {
    public JSONObject search(String  date, Integer startpage,Integer size, String keyword) throws IOException;
}
