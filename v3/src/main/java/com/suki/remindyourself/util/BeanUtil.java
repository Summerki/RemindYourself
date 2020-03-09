package com.suki.remindyourself.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 为了在po对象里使用IOC里的对象
 * 参考：https://www.cnblogs.com/biaogejiushibiao/p/10061466.html
 */
@Component
public class BeanUtil implements ApplicationContextAware { // 必须继承ApplicationContextAware接口

    private static  ApplicationContext applicationContext = null;

    // ApplicationContextAware接口必须实现的方法，通过这个方法把ApplicationContext带过来
    @Override
    public void setApplicationContext(ApplicationContext arg) throws BeansException {
        if (applicationContext == null) {
            applicationContext = arg;
        }
    }

    // 获取ApplicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 获取ApplicationContext里的指定bean
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz); // 这是通过类的方法
    }
}
