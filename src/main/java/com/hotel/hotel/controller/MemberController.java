package com.hotel.hotel.controller;


import com.hotel.hotel.domain.Log;
import com.hotel.hotel.domain.Room;
import com.hotel.hotel.domain.RoomOrderInfo;
import com.hotel.hotel.domain.User;
import com.hotel.hotel.service.LogService;
import com.hotel.hotel.service.RoomOrderInfoService;
import com.hotel.hotel.service.RoomService;
import com.hotel.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;

/**
 * Member 控制器
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomOrderInfoService roomOrderInfoService;

    @Autowired
    private LogService logService;


    /**
     * 用户自行注册
     * @param password
     * @param name
     * @param sex
     * @param telephone
     * @param address
     * @param email
     * @param remark
     * @return
     */

    @PostMapping("/regist")
    public ModelAndView regist(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "sex") String sex,
            @RequestParam(value = "telephone") String telephone,
            @RequestParam(value = "address",required = false,defaultValue = "") String address,
            @RequestParam(value = "email",required = false,defaultValue = "") String email,
            @RequestParam(value = "remark",required = false,defaultValue = "") String remark,
            Model model
            ){

        String message;

        //用户已存在
        if(userService.usernameExist(username)){
            message = new String("此用户名已存在，请您直接登录");

            model.addAttribute("registMessage",message);
            model.addAttribute("loginMessage",null);
            return new ModelAndView("login","userModel",model);
        }

        //手机已被使用
        if(userService.telExist(telephone)){
            message = new String("此手机号已被使用");

            model.addAttribute("registMessage",message);
            return new ModelAndView("regist","userModel",model);
        }

        User user = new User(username,name,sex,password,telephone,address,email,remark);
        userService.registerUser(user);
        model.addAttribute("registMessage",null);
        model.addAttribute("loginMessage",null);
        return new ModelAndView("login","userModel",model);
    }

    /**
     * 用户登录后转房间列表
     * 登录成功：重定向到 /order/{id}
     * 登录失败：返回登录界面 login
     * @param username
     * @param password
     * @param model
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            Model model){
        String message;

        User user = userService.getUserByUsername(username);
        if(user == null) {
            message = "登录失败，用户名不存在";
        }
        else {
            if (user.getPassword().equals(password)) {

                //登陆成功后 重定向到 list 控制器
                return new ModelAndView("redirect:list/"+user.getId());
            } else {
                message = "登录失败，密码错误";
            }
        }
        model.addAttribute("loginMessage",message);
        model.addAttribute("registMessage",null);
        return new ModelAndView("login","userModel",model);
    }

    /**
     * 由顾客查看预定房间
     *
     * 获取空房间,得到5种列表：
     * pageSingle   listSingle   单人空房列表
     * pageDouble   listDouble   双人空房列表
     * pageQued     listQued     四人空房列表
     * pageHourly   listHourly   钟点空房列表
     * pagePres     listPres    总统套房空房列表
     *
     * 页面切换至：
     * 用户查看房间信息
     * roomList
     * @param model
     * @return
     */
    @GetMapping("/list/{id}")
    public ModelAndView roomList(@PathVariable("id") Long id,
            Model model) {

        //获取不同种类的空房间列表
        //获取全部空房
        List<Room> listA = roomService.getRoomsByStatus("empty"); //当前所在页面数据列表
        model.addAttribute("listAll",listA);

        //获取单人间空房
        List<Room> listS = roomService.getRoomsByStatusAndType("empty","single");
        model.addAttribute("listSingle",listS);

        //获取双人间空房
        List<Room> listD = roomService.getRoomsByStatusAndType("empty","double");
        model.addAttribute("listDouble",listD);

        //获取四人间空房
        List<Room> listQ = roomService.getRoomsByStatusAndType("empty","quad");
        model.addAttribute("listQued",listQ);

        //获取钟点房空房
        List<Room> listH = roomService.getRoomsByStatusAndType("empty","hourly");
        model.addAttribute("listHourly",listH);

        //获取总统套房空房
        List<Room> listP = roomService.getRoomsByStatusAndType("empty","pres");
        model.addAttribute("listPres",listP);

        //获取用户
        User user = userService.getUserById(id);
        model.addAttribute("user",user);

        return new ModelAndView("room/roomList","userModel",model);
    }

    /**
     * 根据用户选择的房间 ID 与 用户自己的 ID 查看对应房间
     * 返回页面为 roomInfo
     * @param roomId
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/info/{roomId}/{userId}")
    public ModelAndView info(@PathVariable("roomId") String roomId,
                             @PathVariable("userId") Long userId,
                             Model model){
        User user = userService.getUserById(userId);
        Room room = roomService.getRoomById(roomId);
        model.addAttribute("user",user);
        model.addAttribute("room",room);
        return new ModelAndView("room/roomInfo","userModel",model);
    }


    /**
     * 用户预定使用
     * 已有预订信息 返回 Judge 页面
     * 没有预定信息 返回 预订信息页面
     * @param roomId
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/order/{roomId}/{userId}")
    public ModelAndView order(@PathVariable("roomId") String roomId,
                              @PathVariable("userId") Long userId,
                              Model model){
        User user = userService.getUserById(userId);
        Room room = roomService.getRoomById(roomId);

        model.addAttribute("user",user);
        model.addAttribute("room",room);

        if(!roomOrderInfoService.telNoOrdered(user.getTelephone())) {
            RoomOrderInfo roomOrderInfo = roomOrderInfoService.getInfoByTel(user.getTelephone());
            model.addAttribute("orderInfo",roomOrderInfo);

            //用户手机已被预定
            return new ModelAndView("room/roomOrderJudge", "userModel", model);
        }

        //保存新的预定信息
        RoomOrderInfo roomOrderInfo = roomOrderInfoService.save(new RoomOrderInfo(user.getName(),user.getTelephone(),room.getRoomId(),"ordered",new java.util.Date(),null));

        //保存预定 Log
        Log log = logService.save(new Log(userId,roomId,"预定信息"));

        //修改房间状态信息
        roomService.changeStatus(roomId,"ordered");

        model.addAttribute("roomOrderInfo", roomOrderInfo);
        //用户手机未被预定
        return new ModelAndView("room/roomOrder","userModel",model);
    }

    /**
     * 接受 roomOrderInfo ID
     * 返回 展示预订信息界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/myorder/{id}")
    public ModelAndView myOrder(@PathVariable("id") Long id, Model model){
        RoomOrderInfo roomOrderInfo = roomOrderInfoService.getInfoById(id);
        User user = userService.getUserByTel(roomOrderInfo.getTel());
        model.addAttribute("roomOrderInfo",roomOrderInfo);
        model.addAttribute("user",user);
        return new ModelAndView("room/roomOrder","userModel",model);
    }
}
