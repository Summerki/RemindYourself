package com.suki.remindyourself.web;

import com.suki.remindyourself.service.EventService;
import com.suki.remindyourself.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * register.html 对应的 controller
 */
@Controller
@Slf4j
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    /**
     * 接收前端传来的格式：{username: xx}
     * 返回格式：{result: true/false}  // 有这个人返回false，没有这个人返回true
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkUsername")
    public Map<String, Object> checkUsername(@RequestParam("username")String username) {
        log.info("username {}", username);
        Map<String, Object> map = new HashMap<>();
        if (userService.checkUsername(username)) {
            map.put("result", true);
        } else {
            map.put("result", false);
        }
        return map;
    }

    /**
     * 接收前端json:{email:xx}
     * 返回格式：{result: true/false}  // 有这个邮箱返回false，没有这个邮箱返回true
     * @return
     */
    @RequestMapping("/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestParam("email")String email) {
        log.info("email {}", email);
        Map<String, Object> map = new HashMap<>();
        if (userService.checkEmail(email)) {
            map.put("result", true);
        } else {
            map.put("result", false);
        }
        return map;
    }

    /**
     * 前端传来的json格式：{username:xx, password:xx, email:xx}
     * 返回格式：{result: ''success'/'fail'}
     * @param username
     * @param password
     * @param email
     * @return
     */
    @RequestMapping("/addNewUser")
    @ResponseBody
    public Map<String, Object> addNewUser(@RequestParam("username")String username,
                                          @RequestParam("password")String password,
                                          @RequestParam("email")String email) {
        log.info("username {}, password {}, email {}", username, password, email);
        int res = userService.saveUser(username, password, email);
        Map<String, Object> map = new HashMap<>();
        if (res == 1) {
            map.put("result", "success");
            // 下面这行是我为了偷懒不想做后端某些校验了，以后想细化可以删除这句话……
            eventService.saveEvent("2020-01-01 12:00:00", "2020-01-01 12:00:00", "格式示例", 1,
                                    Integer.parseInt(userService.checkUser(username, password).getId().toString()));
        } else {
            map.put("result", "fail");
        }
        return map;
    }
}
