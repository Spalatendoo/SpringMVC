package com.lk.controller;

import com.lk.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/t1")
    public String test1(@RequestParam("username") String name , Model model){
        // 1、接收前端参数
        System.out.println("接收到的前端参数为" + name);
        //2、将返回的结果传递给前端，Model
        model.addAttribute("msg",name);
        //3、 视图跳转
        return "test";

    }

    //前端接收的是一个对象 ： id name age

    /*
    * 1. 接收前端用户传递的参数，判断参数的名字，假设名字直接在方法上，可以直接使用
    * 2. 假设传递的是一个对象User，匹配User对象中的字段名，如果名字一致就OK，否则，匹配不到
    * */
    @GetMapping("/t2")
    public String test2(User user){
        System.out.println(user);
        return "test";
    }
    @GetMapping("/t3")
    public String test3(String name,ModelMap map){

        //封装要显示到视图中的数据
        //相当于req.setAttribute("name",name)
        map.addAttribute("name",name);
        System.out.println(name);
        return "test";
    }
}
