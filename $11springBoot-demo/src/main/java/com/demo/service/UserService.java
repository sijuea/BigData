package com.demo.service;

import com.demo.entity.User;
import com.demo.entity.UserExample;
import com.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author sijue
 * @date 2021/5/25 11:43
 * @describe
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @return
     */
    public List<User> select(){
        UserExample userExample=new UserExample();
//        userExample.createCriteria().andIdEqualTo(user.getId());
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }


    public Integer insert(User user){
        Integer res = userMapper.insert(user);
        return res;
    }

    public Integer update(User user){
        UserExample userExample=new UserExample();
        userExample.createCriteria().andIdEqualTo(user.getId());
        return userMapper.updateByExample(user,userExample);
    }
    public Integer delete(User user){
        UserExample userExample=new UserExample();
        userExample.createCriteria().andIdEqualTo(user.getId());
        return userMapper.deleteByExample(userExample);
    }

}
