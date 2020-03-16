package com.suki.remindyourself.web;

import com.suki.remindyourself.util.CheckUserAgentUtils;
import com.suki.remindyourself.vo.ErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 对应 index.html 的controller
 */
@Controller
@Slf4j
public class IndexController {

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        if (CheckUserAgentUtils.checkAgentIsMobile(request.getHeader("user-agent"))) {
            return "mobile/index.m";
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        if (CheckUserAgentUtils.checkAgentIsMobile(request.getHeader("user-agent"))) {
            return "mobile/login.m";
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request) {
        if (CheckUserAgentUtils.checkAgentIsMobile(request.getHeader("user-agent"))) {
            return "mobile/register.m";
        }
        return "register";
    }


    // 下面是一个自定义函数，用于返回自定义错误页面后在通过ajax请求这个接口通过session拿到errorVo对象
    // 给浏览器返回这个errorVo对象
    // 格式为:
    /*
     * {
     *   "requestErrorTime":xx,
     *   "requestUrl":xx,
     *   "status":xx,
     *   "statusCodeInfo":xx,
     *   "errorMsg":xx
     * }
     * */
    @RequestMapping("/errorVo")
    @ResponseBody
    public ErrorVo getErrorVoInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ErrorVo errorVo = (ErrorVo) session.getAttribute("errorVo");
        // 拿到对象后移除session中的errorVo
        session.removeAttribute("errorVo");
        return errorVo;
    }

}
