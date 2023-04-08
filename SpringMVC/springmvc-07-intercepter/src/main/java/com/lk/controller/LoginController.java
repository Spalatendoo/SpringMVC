package com.lk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class LoginController {
    @RequestMapping("/main")
    public String main(){
        return "main";
    }
    @RequestMapping("/goLogin")
    public String login(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(HttpSession session,String username, String password){
        System.out.println("login ==>"+ username);
//把用户的信息存在session中
        session.setAttribute("userLoginInfo",username);
        return "main";
    }
    @RequestMapping("/goOut")
    public String goOut(HttpSession session){
        session.removeAttribute("userLoginInfo");
        return "main";
    }
}
