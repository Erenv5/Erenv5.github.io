package com.hotel.hotel.controller;

import com.hotel.hotel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//主页控制器
@Controller
@RequestMapping("/")
public class MainController {
    private UserService userService;
}
