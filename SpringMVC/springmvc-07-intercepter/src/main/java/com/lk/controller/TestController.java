package com.lk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/t1")
    public String test(){
        System.out.println("TestController=> test() 执行了");
        return "OK";

    }}
