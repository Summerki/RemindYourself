package com.suki.remindyourself.web;

import com.suki.remindyourself.util.CheckUserAgentUtils;
import com.suki.remindyourself.vo.UserTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class TestController {

    @RequestMapping("/")
    public String test(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        log.info("user-agent {}", userAgent);
        if (CheckUserAgentUtils.checkAgentIsMobile(userAgent)) { // 移动端
            return "mobile/index.m";
        }
        return "index";
    }

    @Autowired
    UserTable userTable;

    @ResponseBody
    @RequestMapping("/test")
    public String test2() {
        log.info("test {}", userTable.toString());
        return "success";
    }

}
