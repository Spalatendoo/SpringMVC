package com.lk.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lk.pojo.User;
import com.lk.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @RequestMapping( "/j1")
 //   @ResponseBody  //加了这个注解，不会走视图解析器，会直接返回一个字符串
    public String json1() throws JsonProcessingException {

        //jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user = new User("lk",23,"男");
        String str = mapper.writeValueAsString(user);
        return str;
    }

    @RequestMapping("/j2")
    public String json2() throws JsonProcessingException {

        //jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        List<User> list = new ArrayList<User>();
        //创建一个对象
        User user1 = new User("lk",23,"男");
        User user2 = new User("lk",23,"男");
        User user3 = new User("lk",23,"男");
        User user4 = new User("lk",23,"男");

        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        return JsonUtils.getJson(list);
    }

    @RequestMapping("/j3")
    public String json3() throws JsonProcessingException {

        Date date = new Date();
        return JsonUtils.getJson(date);
    }

    @RequestMapping("/j4")
    public String json4() throws JsonProcessingException {
        List<User> list = new ArrayList<User>();
        User user1 = new User("lk",23,"男");
        User user2 = new User("lk",23,"男");
        User user3 = new User("lk",23,"男");
        User user4 = new User("lk",23,"男");
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        String s = JSON.toJSONString(list);
        return s;
    }
}
