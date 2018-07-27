package com.hotel.hotel.controller;

import com.hotel.hotel.domain.User;
import com.hotel.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView login(Model model){
        String loginMessage = "欢迎登录";
        String registMessage = null;
        model.addAttribute("registMessage",registMessage);
        model.addAttribute("loginMessage",loginMessage);
        return new ModelAndView("login","userModel",model);
    }


    /**
     * 获取用户注册页面
     * @return
     */
    @GetMapping("/regist")
    public ModelAndView regist(Model model){
        String registMessage = null;
        model.addAttribute("registMessage",registMessage);
        return new ModelAndView("regist","userModel",model);
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
