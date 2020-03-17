package com.suki.remindyourself.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * 测试使用
 */
@Controller
@Slf4j
public class TestController {

//    /**
//     * quartz测试
//     * @return
//     */
//    @RequestMapping("/test")
////    @ResponseBody
//    public String test() {
////        int i = 1 / 0;
//        return "test/test";
//    }
//
//    @Autowired
//    MyQuartzUtils myQuartzUtils;
//
//    /**
//     * 测试启动quartz
//     * @return
//     */
//    @RequestMapping("/testStart")
//    @ResponseBody
//    public String testStart() throws SchedulerException {
//        myQuartzUtils.start();
//        return "success";
//    }
//
//    @RequestMapping("/testStop")
//    @ResponseBody
//    public String testStop() throws SchedulerException {
//        myQuartzUtils.stop();
//        return "success";
//    }
//
//    @RequestMapping("/testResume")
//    @ResponseBody
//    public String testResume() throws SchedulerException {
//        myQuartzUtils.resume();
//        return "success";
//    }
//
//    @RequestMapping("/testCancel")
//    @ResponseBody
//    public String testCancel() throws SchedulerException {
//        myQuartzUtils.remove();
//        return "success";
//    }
//
//
//    @RequestMapping("/testAdd")
//    @ResponseBody
//    public String testAdd() throws SchedulerException {
//
//        myQuartzUtils.add();
//        return "success";
//    }
//
//    @Autowired
//    EmailUtils emailUtils;
//
//    @RequestMapping("/testEmail")
//    @ResponseBody
//    public String testEmail() {
//        emailUtils.sendMail("591593076@qq.com", "测试邮件", "测试内容");
//
//        return "success";
//    }
}
