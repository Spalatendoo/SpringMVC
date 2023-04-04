package com.lk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@RequestParam("username") :username是提交的域名的参数名
@Controller
public class ModelTest1 {
    @RequestMapping("/hello")
    public String test(@RequestParam("username") String name){
        System.out.println(name);
        return "test";
    }
}
