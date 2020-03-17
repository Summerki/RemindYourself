package com.suki.remindyourself.quartz;

import com.suki.remindyourself.email.EmailUtils;
import com.suki.remindyourself.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyQuartzJob extends QuartzJobBean {


    @Autowired
    EmailUtils emailUtils;

    @Autowired
    EventService eventService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        // 拿出需要的数据
        String username = jobDataMap.getString("username");
        String userEmail = jobDataMap.getString("userEmail");
        String establishTime = jobDataMap.getString("establishTime");
        String remindTime = jobDataMap.getString("remindTime");
        String remindContent = jobDataMap.getString("remindContent");
        log.info("username {}, userEmail {}, establishTime {}, remindTime {}, remindContent {}",
                username, userEmail, establishTime, remindTime, remindContent);

        // 执行任务
        emailUtils.sendMail(username, userEmail, establishTime, remindTime, remindContent);

        if (eventService.updateEventStateTo1(establishTime, remindTime, remindContent) == 1) {
            log.info("quartz定时任务完成后更新成功");
        } else {
            log.info("quartz定时任务完成后更新失败");
        }
    }
}
