package com.hotel.hotel.controller;


import com.hotel.hotel.domain.*;
import com.hotel.hotel.service.clerk.ClerkService;
import com.hotel.hotel.service.member.UserService;
import com.hotel.hotel.service.room.RoomLiveInfoService;
import com.hotel.hotel.service.room.RoomOrderInfoService;
import com.hotel.hotel.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/clerk")
public class ClerkController {

    @Autowired
    private ClerkService clerkService;

    @Autowired
    private RoomOrderInfoService roomOrderInfoService;

    @Autowired
    private RoomLiveInfoService roomLiveInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

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
                    model.addAttribute("messageFromLive",messageFromLive);
                    model.addAttribute("messageFromLeave",messageFromLeave);
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
    public ModelAndView live(@PathVariable(value = "id") Long clerkId,
                             @RequestParam(value = "liveTel") String tel,
                             Model model){
        String messageFromLive;
        String messageFromLeave = null;

        Clerk clerk = clerkService.getById(clerkId);
        model.addAttribute("clerk",clerk);

        //表单中手机号没有预定房间
        if(roomOrderInfoService.telNoOrdered(tel)){
            messageFromLive = "该用户没有预定房间";
            model.addAttribute("messageFromLive",messageFromLive);
            model.addAttribute("messageFromLeave",messageFromLeave);
            return new ModelAndView("clerk/fontDeskIndex","userModel",model);
        }

        //手机号预定了房间,到的页面可以转入住
        RoomOrderInfo roomOrderInfo = roomOrderInfoService.getInfoByTel(tel);
        model.addAttribute("roomOrderInfo",roomOrderInfo);
        model.addAttribute("clerk",clerk);
        return new ModelAndView("room/roomOrderInfo","userModel",model);
    }

    /**
     * 入住信息结账
     * 入住信息不存在 转前台主界面
     * 入住信息存在 转 roomLiveInfo
     * @param clerkId
     * @param tel
     * @param model
     * @return
     */
    @PostMapping("/leave/{id}")
    public ModelAndView leave(@PathVariable(value = "id") Long clerkId,
                              @RequestParam(value = "leaveTel") String tel,
                              Model model){

        String messageFromLeave;
        String messageFromLive = null;

        Clerk clerk = clerkService.getById(clerkId);
        model.addAttribute("clerk",clerk);

        //表单中手机号没有入住信息
        if(roomLiveInfoService.telNoLived(tel)){
            messageFromLeave = "该用户没有入住房间";
            model.addAttribute("messageFromLive",messageFromLive);
            model.addAttribute("messageFromLeave",messageFromLeave);
            return new ModelAndView("clerk/fontDeskIndex","userModel",model);
        }

        // 手机号有入住信息，到的页面点结账
        RoomLiveInfo roomLiveInfo = roomLiveInfoService.getByTel(tel);
        model.addAttribute("roomLiveInfo",roomLiveInfo);
        model.addAttribute("clerk",clerk);
        return new ModelAndView("room/roomLiveInfo","userModel",model);
    }
    /**
     * 入住信息创建保存
     * 返回前台主界面
     * @param clerkId
     * @param infoId
     * @param model
     * @return
     */
    @GetMapping("/live/{clerkId}/{infoId}")
    public ModelAndView toLive(@PathVariable(value = "clerkId") Long clerkId,
                               @PathVariable(value = "infoId") Long infoId,
                               Model model) {
        //获取各种对象
        Clerk clerk = clerkService.getById(clerkId);
        RoomOrderInfo roomOrderInfo = roomOrderInfoService.getInfoById(infoId);
        User user = userService.getUserByTel(roomOrderInfo.getTel());
        Room room = roomService.getRoomById(roomOrderInfo.getRoomId());

        //更改房间状态
        roomService.changeStatus(roomOrderInfo.getRoomId(),"lived");

        //创建并保存 live 并删除 order
        RoomLiveInfo roomLiveInfo = new RoomLiveInfo(user.getName(),user.getTelephone(),roomOrderInfo.getRoomId(),room.getStatus(),new Date(),"",
                clerk.getName(),room.getPrice(),"");
        roomLiveInfoService.save(roomLiveInfo);
        roomOrderInfoService.delete(infoId);
        model.addAttribute("info",roomLiveInfo);
        return new ModelAndView("clerk/fontDeskIndex","userModel",model);
    }

    @GetMapping("/leave/{clerkId}/{infoId}")
    public ModelAndView toLeave(@PathVariable(value = "clerkId") Long clerkId,
                                @PathVariable(value = "infoId") Long infoId,
                                Model model){

        //获取各种对象
        Clerk clerk = clerkService.getById(clerkId);
        RoomLiveInfo roomLiveInfo = roomLiveInfoService.getById(infoId);
        User user = userService.getUserByTel(roomLiveInfo.getTel());
        Room room = roomService.getRoomById(roomLiveInfo.getRoomId());

        //更改房间状态
        roomService.changeStatus(roomLiveInfo.getRoomId(),"empty");

        //删除 live
        roomLiveInfoService.delete(infoId);

        return new ModelAndView("clerk/fontDeskIndex","userModel",model);
    }
}