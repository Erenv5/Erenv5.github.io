package com.hotel.hotel.controller;

import com.hotel.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//主页控制器
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "memberLogin";
    }

    @GetMapping("/regist")
    public String regist(){
        return "memberRegist";
    }
}
