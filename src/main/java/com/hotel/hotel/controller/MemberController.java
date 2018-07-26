package com.hotel.hotel.controller;


import com.hotel.hotel.domain.Room;
import com.hotel.hotel.domain.User;
import com.hotel.hotel.service.RoomService;
import com.hotel.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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


        Boolean regist;
        String message;

        //用户已存在
        User user = userService.getUserByUsername(username);
        if(user != null){
            regist = false;
            message = new String("此用户名已存在，请您直接登录");

            model.addAttribute("regist",regist);
            model.addAttribute("registMessage",message);
            return new ModelAndView("/login","userModel",model);
        }

        user = new User(username,name,sex,password,telephone,address,email,remark);
        userService.registerUser(user);
        return new ModelAndView("/member/roomOrder");
    }

    /**
     * 用户登录
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

        Boolean loginError;
        String message;

        User user = userService.getUserByUsername(username);
        if(user == null) {
            loginError = true;
            message = "登录失败，用户名不存在";
        }
        else {
            if (user.getPassword().compareTo(password) == 1) {
                //把该用户放model
                model.addAttribute("theUser",user);
                //登陆成功后 重定向到 order 控制器
                return new ModelAndView("/member/roomOrder","usermodel",model);
            } else {
                loginError = true;
                message = "登录失败，密码错误";
            }
        }
        model.addAttribute("loginError",loginError);
        model.addAttribute("loginMessage",message);
        return new ModelAndView("/login","usermodel",model);
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
    public ModelAndView orderRoom(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model) {
        //获取 Pageable 对象
        Pageable pageable = new PageRequest(pageIndex,pageSize);

        //获取不同种类的空房间列表
        //获取单人间空房
        Page<Room> pageS = roomService.getRoomsByStatusAndType("empty","single",pageable);
        List<Room> listS = pageS.getContent();  //当前所在页面数据列表
        model.addAttribute("pageSingle",pageS);
        model.addAttribute("listSingle",listS);

        //获取双人间空房
        Page<Room> pageD = roomService.getRoomsByStatusAndType("empty","double",pageable);
        List<Room> listD = pageS.getContent();  //当前所在页面数据列表
        model.addAttribute("pageDouble",pageD);
        model.addAttribute("listDouble",listD);

        //获取四人间空房
        Page<Room> pageQ = roomService.getRoomsByStatusAndType("empty","quad",pageable);
        List<Room> listQ = pageS.getContent();  //当前所在页面数据列表
        model.addAttribute("pageQued",pageQ);
        model.addAttribute("listQued",listQ);

        //获取钟点房空房
        Page<Room> pageH = roomService.getRoomsByStatusAndType("empty","hourly",pageable);
        List<Room> listH = pageS.getContent();  //当前所在页面数据列表
        model.addAttribute("pageHourly",pageH);
        model.addAttribute("listHourly",listH);

        //获取总统套房空房
        Page<Room> pageP = roomService.getRoomsByStatusAndType("empty","pres",pageable);
        List<Room> listP = pageS.getContent();  //当前所在页面数据列表
        model.addAttribute("pagePres",pageP);
        model.addAttribute("listPres",listP);


        return new ModelAndView("member/member/roomOrder","userModel",model);
    }

}
