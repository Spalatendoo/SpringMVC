package com.lk.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//只要实现了Controller 接口的类，说明这就是一个控制器了
@Controller
public class ControllerTest1  {
    @RequestMapping("/t2")
   public String test1(Model model){
       model.addAttribute("msg","Anotation");

       return "test";
   }
    @RequestMapping("/t3")
    public String test2(Model model){
        model.addAttribute("msg","Anotation");

        return "test";
    }
}
