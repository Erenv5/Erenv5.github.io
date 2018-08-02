package com.hotel.hotel.controller;


import com.hotel.hotel.domain.*;
import com.hotel.hotel.service.clerk.ClerkService;
import com.hotel.hotel.service.log.LogService;
import com.hotel.hotel.service.member.UserService;
import com.hotel.hotel.service.room.RoomLiveInfoService;
import com.hotel.hotel.service.room.RoomOrderInfoService;
import com.hotel.hotel.service.room.RoomService;
import com.hotel.hotel.utils.DifferentDaysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private LogService logService;

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

        //计算总价并传入 Model
        Date date = new Date();
        int days = DifferentDaysUtil.differentDays(roomLiveInfo.getLiveTime(),date);
        if(days == 0)
            days =1;
        long price = Long.parseLong(roomLiveInfo.getPrice()) * days;
        String prices = String.valueOf(price);
        System.out.println(prices);
        model.addAttribute("price",prices);

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
        String messageFromLeave = null;
        String messageFromLive;

        //更改房间状态
        roomService.changeStatus(roomOrderInfo.getRoomId(),"lived");

        //创建并保存 live 并删除 order
        RoomLiveInfo roomLiveInfo = new RoomLiveInfo(user.getName(),user.getTelephone(),roomOrderInfo.getRoomId(),room.getStatus(),new Date(),"1000",
                clerk.getName(),room.getPrice(),"");
        //添加日志 Log
        Log log = logService.save(new Log(user.getId(),room.getRoomId(),"入住房间"));

        roomLiveInfoService.save(roomLiveInfo);
        roomOrderInfoService.delete(roomOrderInfo.getInfoId());
        messageFromLive="转入住成功";
        model.addAttribute("clerk",clerk);
        model.addAttribute("messageFromLeave",messageFromLeave);
        model.addAttribute("messageFromLive",messageFromLive);
        return new ModelAndView("clerk/fontDeskIndex","userModel",model);
    }

    /**
     * 结账操作
     * 返回前台主页
     * @param clerkId
     * @param infoId
     * @param model
     * @return
     */
    @GetMapping("/leave/{clerkId}/{infoId}")
    public ModelAndView toLeave(@PathVariable(value = "clerkId") Long clerkId,
                                @PathVariable(value = "infoId") Long infoId,
                                Model model){

        String messageFromLeave;
        String messageFromLive = null;
        //获取各种对象
        Clerk clerk = clerkService.getById(clerkId);
        RoomLiveInfo roomLiveInfo = roomLiveInfoService.getById(infoId);
        User user = userService.getUserByTel(roomLiveInfo.getTel());
        Room room = roomService.getRoomById(roomLiveInfo.getRoomId());

        //更改房间状态
        roomService.changeStatus(roomLiveInfo.getRoomId(),"empty");

        //删除 live
        roomLiveInfoService.delete(infoId);

        //添加日志 Log
        Log log = logService.save(new Log(user.getId(),room.getRoomId(),"结账退房"));

        //设置Model
        messageFromLeave = "结账完成";
        model.addAttribute("clerk",clerk);
        model.addAttribute("messageFromLeave",messageFromLeave);
        model.addAttribute("messageFromLive",messageFromLive);
        return new ModelAndView("clerk/fontDeskIndex","userModel",model);
    }

    /**
     * 获取所有房间信息
     * @param model
     * @return
     */
    @GetMapping("/rooms")
    public ModelAndView rooms(Model model){
        List<Room> roomList = roomService.getAllRooms();
        String message = null;
        model.addAttribute("rooms",roomList);
        model.addAttribute("message",message);
        return new ModelAndView("clerk/manager/rooms","userModel",model);
    }

    /**
     * 获取所有日志信息
     * @param model
     * @return
     */
    @GetMapping("/logs")
    public ModelAndView logs(Model model){
        List<Log> logList = logService.getAllLogs();
        model.addAttribute("logs",logList);
        return new ModelAndView("clerk/manager/logs","userModel",model);
    }

    /**
     * 转到对应操作
     *
     * @param roomId    需要输入对的 ID
     * @param model
     * @return
     */
//    @GetMapping("/roomOp/{id}")
//    public ModelAndView roomOp(@PathVariable("id") String roomId, Model model){
//        String message = null;
//        model.addAttribute("message",message);
//        model.addAttribute("id",roomId);
//        return new ModelAndView("clerk/manager/roomOperate","userModel",model);
//
//    }
//
//    @GetMapping("/logs/{id}")
//    public ModelAndView logOp(@PathVariable("id") Long clerkId,
//                              Model model){
//        Log log = logService.getLogById(clerkId);
//        model.addAttribute("log",log);
//        return new ModelAndView("clerk/manager/logOperate");
//    }

    /**
     * 由 roomOperate 过来
     * 修改或增加房间，返回到 rooms 列表
     * 假如房间不为空，返回到 roomOperator 页面
     * @param roomId
     * @param floor
     * @param type
     * @param status
     * @param normMemberPrice
     * @param vipMemberPrice
     * @param price
     * @param remark
     * @param model
     * @return
     */
    @PostMapping("/room")
    public ModelAndView insertRoom(@RequestParam("roomId") String roomId,
                               @RequestParam("floor") String floor,
                               @RequestParam("type") String type,
                               @RequestParam("status") String status,
                               @RequestParam("normMemberPrice") String normMemberPrice,
                               @RequestParam("vipMemberPrice") String vipMemberPrice,
                               @RequestParam("price") String price,
                               @RequestParam("remark") String remark,
                               Model model){
        if(!roomService.ifRoomEmpty(roomId)){
            String message = "房间不为空，不能修改！";
            model.addAttribute("message",message);
            return new ModelAndView("clerk/manager/roomOperate","userModel",model);
        }

        Room room = new Room(roomId,type,status,floor,price,normMemberPrice,vipMemberPrice,remark);
        roomService.saveOrUpdateRoom(room);
        List<Room> roomList = roomService.getAllRooms();
        model.addAttribute("rooms",roomList);
        return new ModelAndView("clerk/manager/rooms","userModel",model);
    }

    @GetMapping("/manager")
    public String manageIndex(){
        return "clerk/managerIndex";
    }

    @GetMapping("/clerks")
    public String clerks(){
        return "clerk/manager/clerks";
    }

    @PostMapping("/clerk")
    public ModelAndView clerkOp(){
        return new ModelAndView();
    }

}