package com.suki.remindyourself.web;

import com.suki.remindyourself.vo.ErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义处理error视图和error数据的 controller
 */
@Controller
@Slf4j
public class MyErrorController extends BasicErrorController {

    @Autowired
    public MyErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    /**
     * 返回 自定义error 视图
     * @param request
     * @param response
     * @return
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, false); // 第二个参数[includeStackTrace]表示是否会记录error的trace
        // 例如：{timestamp=Tue Mar 10 10:19:01 CST 2020, status=500, error=Internal Server Error, message=/ by zero, path=/remindYourself/test}
        log.info("errorAttributes {}", errorAttributes);

        // 构建errorVo对象
        ErrorVo errorVo = new ErrorVo();
        errorVo.setRequestErrorTime((Date) errorAttributes.get("timestamp"));
        errorVo.setRequestUrl(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + (String) errorAttributes.get("path"));
        errorVo.setStatus(errorAttributes.get("status").toString());
        errorVo.setStatusCodeInfo((String) errorAttributes.get("error"));
        errorVo.setErrorMsg((String) errorAttributes.get("message"));
        // 将errorVo对象存入session
        HttpSession session = request.getSession();
        session.setAttribute("errorVo", errorVo);

        return new ModelAndView("error/error"); // 将所有的error都返回到 error/error.html
    }

    /**
     * 返回自定义的error数据
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, false);

        Map<String, Object> body = new HashMap<>();
        body.put("requestErrorTime", (Date) errorAttributes.get("timestamp"));
        body.put("requestUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + (String) errorAttributes.get("path"));
        body.put("status", errorAttributes.get("status").toString());
        body.put("statusCodeInfo", (String) errorAttributes.get("error"));
        body.put("errorMsg", (String) errorAttributes.get("message"));

        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(body, status);
    }


}
