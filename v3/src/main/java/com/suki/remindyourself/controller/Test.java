package com.suki.remindyourself.controller;

import com.suki.remindyourself.util.CheckUserAgentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    @RequestMapping("/indexTest")
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String header = request.getHeader("user-agent");
        if (CheckUserAgentUtil.checkAgentIsMobile(header)) {
            logger.info("来自移动端的访问 header={} context-path={}", header, request.getContextPath());
            logger.warn("url={}", request.getRequestURL());
//            response.sendRedirect("/index");
            return "mobile/index.m";
        } else {
            logger.info("来自桌面端的访问 header={} context-path={}", header, request.getContextPath());
            logger.warn("url={}", request.getRequestURL());
//            response.sendRedirect(request.getContextPath() + "/index.test");
            return "index";
        }
    }
}
