package com.hotel.hotel.controller;


import com.hotel.hotel.domain.Clerk;
import com.hotel.hotel.domain.RoomOrderInfo;
import com.hotel.hotel.service.clerk.ClerkService;
import com.hotel.hotel.service.room.RoomOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/clerk")
public class ClerkController {

    @Autowired
    private ClerkService clerkService;

    @Autowired
    private RoomOrderInfoService roomOrderInfoService;

    @GetMapping
    public ModelAndView index(Model model){
        String loginMessage = "欢迎登录";
        String registMessage = null;
        model.addAttribute("loginMessage",loginMessage);
        model.addAttribute("registMessage",registMessage);
        return new ModelAndView("clerk/login","userModel",model);
    }

    /**
     * 员工登录
     * 如果是前台 切到 fontDeskIndex
     * 如果是经理 切到 managerIndex
     * @param username
     * @param password
     * @param model
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "username", required = true) String username,
                              @RequestParam(value = "password", required = true) String password,
                              Model model){

        String loginMessage;
        String registMessage = null;
        String messageFromLive = null;
        String messageFromLeave = null;

        //如果用户名存在
        if(clerkService.usernameExists(username)){
            //如果密码正确
            if(clerkService.passwordCorrect(username,password)) {
                Clerk clerk = clerkService.getByUsername(username);
                model.addAttribute("clerk",clerk);
                //如果员工是前台
                if (clerk.getPermission().compareTo("fontDesk") == 0) {
                    model.addAttribute(messageFromLive);
                    model.addAttribute(messageFromLeave);
                    return new ModelAndView("clerk/fontDeskIndex", "userModel", model);
                }
                //如果员工是经理
                else {
                    return new ModelAndView("clerk/managerIndex", "userModel", model);
                }
            }
            //密码不正确
            loginMessage = "登录失败，密码错误";
            model.addAttribute("loginMessage",loginMessage);
            model.addAttribute("registMessage",registMessage);
            return new ModelAndView("clerk/login","userModel",model);
        }

        //用户名不存在
        loginMessage = " 员工账号不存在";
        model.addAttribute("loginMessage",loginMessage);
        model.addAttribute("registMessage",registMessage);
        return new ModelAndView("clerk/login","userModel",model);
    }

    /**
     * 预定房间转入住查询
     * 预定房间信息不存在 返回到前台页面
     * 预定房间信息存在 收押金返回到确认界面
     * @param clerkId
     * @param tel
     * @param model
     * @return
     */
    @PostMapping("/live/{id}")
    public ModelAndView live(@PathVariable("id") Long clerkId,
                             @RequestParam("tel") String tel,
                             Model model){
        String messageFromLive;
        String messageFromLeave = null;

        Clerk clerk = clerkService.getById(clerkId);
        model.addAttribute("clerk",clerk);

        //表单中手机号没有预定房间
        if(roomOrderInfoService.telNoOrdered(tel)){
            messageFromLive = "该手机没有预定房间";
            model.addAttribute("messageFromLive",messageFromLive);
            model.addAttribute("messageFromLeave",messageFromLeave);
            return new ModelAndView("clerk/fontDeskIndex","userModel",model);
        }

        RoomOrderInfo roomOrderInfo = roomOrderInfoService.getInfoByTel(tel);
        return new ModelAndView("");
    }

}