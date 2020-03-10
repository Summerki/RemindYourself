package com.suki.remindyourself.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试使用
 */
@Controller
@Slf4j
public class TestController {

    /**
     * 故意出错测试
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        int i = 1 / 0;
        return "success";
    }


}
