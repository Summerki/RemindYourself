package com.suki.remindyourself.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 用于一启动springboot程序就将schedule启动
 */
@Component
@Slf4j
public class StartSchedule implements ServletContextListener {

    @Autowired
    Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            scheduler.start();
            log.info("schedule启动成功！！！");
        } catch (SchedulerException e) {
            log.error("schedule启动失败！！！");
            e.printStackTrace();
        }
    }
}
