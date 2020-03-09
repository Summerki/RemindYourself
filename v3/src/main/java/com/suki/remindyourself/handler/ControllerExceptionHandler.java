package com.suki.remindyourself.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 对于 controller 层发生的异常都在这里处理
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    // 没有特别指定异常，就代表所有controller的异常都会跑到这里来处理
    @ExceptionHandler
    public String exceptionHandler(HttpServletRequest request,
                                   Exception e) {
        HttpSession session = request.getSession();
        session.setAttribute("url", request.getRequestURL().toString());
        session.setAttribute("errorMsg", e.getMessage());
        log.info("url {} errorMsg {}", request.getRequestURL().toString(), e.getMessage());
        return "/error/error";
    }

}
