package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * get请求：controller接受的参数名最好和url中参数名一致
 * post请求：
 */
/**
 * @author sijue
 * @date 2021/5/25 11:42
 * @describe
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;


    /**@ResponseBody
    //如果返回的类型是八中基本类型和string，会自动返回
    //如果返回是对象就会返回对象的json串
    **/
    @RequestMapping(value = "/select")
    public List<User> getData(){
        List<User> users = userService.select();
        return users;
    }
    @RequestMapping("user")
    /**
     * 如果想使用@RequetBody,需要前端使用ajax 传来的参数包含application/json;
     * 然后后台接收的时候可以使用@RequestBody
     */
    public String insert(Integer id,String  name,Integer age,String  op){
        User user=new User(id,name,age,op);
//        String op=user.getOp();
        switch (op){
            case "insert":
                userService.insert(user);
                return "complete";
            case "update":
                userService.update(user);
                return "complete";
            case "delete":
                userService.delete(user);
                return "complete";
            default:
                return "error";
        }
    }




}
