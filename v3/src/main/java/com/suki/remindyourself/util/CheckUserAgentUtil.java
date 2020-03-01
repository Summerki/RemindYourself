package com.suki.remindyourself.util;

public class CheckUserAgentUtil {
    /**
     * 定义移动端所有可能的请求类型
     */
    private final static String[] agent = {"Andriod", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser"};

    /**
     * 判断 User-Agent 是不是来自移动端
     * @param ua
     * @return
     */
    public static boolean checkAgentIsMobile(String ua) {
        boolean flag = false;
        if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
            // 排除 苹果桌面系统
            if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
                for (String item : agent) {
                    if (ua.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }
}
