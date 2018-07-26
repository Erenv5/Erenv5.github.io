package com.hotel.hotel.controller;

import com.hotel.hotel.domain.User;
import com.hotel.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//主页控制器
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String root(){
        return "index";
    }

    /**
     * 获取用户登录界面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }


    /**
     * 获取用户注册页面
     * @return
     */
    @GetMapping("/regist")
    public String regist(){
        return "regist";
    }

    /**
     * 注册完转到登录
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String registerUser(User user) {
        return "redirect:/login";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }
}
