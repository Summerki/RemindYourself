package com.suki.remindyourself.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 自定义 error 对象
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorVo {
    // 例如：{timestamp=Tue Mar 10 10:19:01 CST 2020, status=500, error=Internal Server Error, message=/ by zero, path=/remindYourself/test}

    @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss",timezone = "GMT+8")
    private Date requestErrorTime; // 请求错误的时间
    private String requestUrl; // 你在浏览器上输入的网址
    private String status; // 状态码
    private String statusCodeInfo; // 对应状态码的解释含义
    private String errorMsg; // 错误信息
}
