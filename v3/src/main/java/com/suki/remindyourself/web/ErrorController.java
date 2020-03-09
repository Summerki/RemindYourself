package com.suki.remindyourself.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 对应 error.html 的controller
 */
@Controller
@Slf4j
public class ErrorController {

    @RequestMapping("/error1")
    @ResponseBody
    public Map<String, Object> error(HttpSession session) {
        String url = (String) session.getAttribute("url");
        String errorMsg = (String) session.getAttribute("errorMsg");
        log.info("url {} errorMsg {}", url, errorMsg);
        Map<String, Object> map = new HashMap<>();
        map.put("url", url);
        map.put("error_msg", errorMsg);
        session.removeAttribute("url");
        session.removeAttribute("errorMsg");
        return map;
    }

}
