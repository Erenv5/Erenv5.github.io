package com.hotel.hotel.controller;


import com.hotel.hotel.domain.Room;
import com.hotel.hotel.domain.User;
import com.hotel.hotel.service.RoomService;
import com.hotel.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.List;

/**
 * 主页控制器
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;


    /**
     * 新建用户
     * @param user
     * @param authorityId
     * @return
     */
//    @PostMapping
//    public ResponseEntity<Response> create(User user, Long authorityId) {
//
//        if(user.getId() == null) {
//            user.setEncodePassword(user.getPassword()); // 加密密码
//        }else {
//            // 判断密码是否做了变更
//            User originalUser = userService.getUserById(user.getId());
//            String rawPassword = originalUser.getPassword();
//            PasswordEncoder  encoder = new BCryptPasswordEncoder();
//            String encodePasswd = encoder.encode(user.getPassword());
//            boolean isMatch = encoder.matches(rawPassword, encodePasswd);
//            if (!isMatch) {
//                user.setEncodePassword(user.getPassword());
//            }else {
//                user.setPassword(user.getPassword());
//            }
//        }
//
//        try {
//            userService.save(user);
//        }  catch (ConstraintViolationException e)  {
//            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
//        }
//
//        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
//    }


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
                //把该用户放model
                model.addAttribute("theUser",user);
                //登陆成功后 重定向到 order 控制器
                return new ModelAndView("redirect:order","userModel",model);
            } else {
                message = "登录失败，密码错误";
            }
        }
        model.addAttribute("loginMessage",message);
        model.addAttribute("registMessage",null);
        return new ModelAndView("login","userModel",model);
    }

    /**
     * 由顾客预定房间
     *
     * 获取空房间,得到5种列表：
     * pageSingle   listSingle   单人空房列表
     * pageDouble   listDouble   双人空房列表
     * pageQued     listQued     四人空房列表
     * pageHourly   listHourly   钟点空房列表
     * pagePres     listPres    总统套房空房列表
     *
     * 页面切换至：
     * 用户房间预定界面
     * @param model
     * @return
     */
    @GetMapping("/order")
    public ModelAndView orderRoom(Model model) {


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


        return new ModelAndView("room/roomList","userModel",model);
    }

}
