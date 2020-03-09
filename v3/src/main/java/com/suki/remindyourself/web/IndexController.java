package com.suki.remindyourself.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 对应 index.html 的controller
 */
@Controller
@Slf4j
public class IndexController {

    @GetMapping("/login")
    public String login() {
        log.info("来了");
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

}
