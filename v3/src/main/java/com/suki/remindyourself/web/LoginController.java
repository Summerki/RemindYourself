package com.suki.remindyourself.web;

import com.suki.remindyourself.po.User;
import com.suki.remindyourself.service.UserService;
import com.suki.remindyourself.util.CheckUserAgentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 对应 login.html 的controller
 */
@Slf4j
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    /**
     * login.html 页面上基本原则判断完后，给服务器判断该用户是否存在
     * @return
     * 返回的json格式为:
     * {
     *     'status':'ok'/'fail'
     * }
     */
    @ResponseBody
    @PostMapping("/checkUser")
    public Map<String, Object> checkUser(@RequestParam("username")String username,
                                         @RequestParam("password")String password,
                                         HttpSession session) {
        log.info("username:{} password:{}", username, password);
        Map<String, Object> map = new HashMap<>();
        User user = userService.checkUser(username, password);
        if (user != null) {
            map.put("status", "ok");
            // 这里还应该将user信息存放到session
            session.setAttribute("user", user);
        } else {
            map.put("status", "fail");
        }
        return map;
    }

    /**
     * 表单验证成功后跳转到 main.html
     * @return
     */
    @RequestMapping("/main")
    public String toMain(HttpServletRequest request) {
        if (CheckUserAgentUtils.checkAgentIsMobile(request.getHeader("user-agent"))) {
            return "mobile/main.m";
        }
        return "main";
    }

}
