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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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


    User user = new User();


    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "password", required =  true) String password,
            Model model){

        user.setId(id);
        user.setPassword(password);
        model.addAttribute("user",user);



        Boolean login;
        String message;
        User user = userService.getUserById(id);
        if(user == null) {
            login = false;
            message = "登录失败，用户名不存在";
        }
        else {
            if (user.getPassword() == password) {
                login = true;
                message = "登录成功";


                //想要登陆成功后 重定向到 order 控制器
                return new ModelAndView("users/member/loginSuccess","usermodel",model);
            } else {
                login = false;
                message = "登录失败，密码错误";
            }
        }
        model.addAttribute("login",login);
        model.addAttribute("message",message);
        return new ModelAndView("users/member/login","usermodel",model);
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


        return new ModelAndView("users/member/roomOrder","userModel",model);
    }

}
