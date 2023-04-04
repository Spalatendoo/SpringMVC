package com.lk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloControler {
    //localhost:8080/hello/h1  如果不要上面的@RequestMapping注解，则访问的是localhost:8080/h1
@RequestMapping("/h1")
    public String hello(Model model ){

        //封装数据
        model.addAttribute("msg","Hello,SpringMVCannotation");

        return "hello";  //会被视图解析器处理
    }
}
