package com.suki.remindyourself.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * quartz配置类
 */
@Configuration
public class QuartzConfig {

    /**
     * 定义要执行的任务类
     * @return
     */
    @Bean
    public JobDetail remindTaskDetail() {
        return JobBuilder.newJob(RemindTask.class).withIdentity("remindYourself-Task").storeDurably().build();
    }

    /**
     * cron触发器定义与设置
     * @return
     */
    @Bean
    public CronTrigger remindTaskCronTrigger() {
        // cron类型：通过cron表达式设置触发规则
        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule(String.format("0 */1 * * * ?"))
                                    .withMisfireHandlingInstructionDoNothing();

        // 一个trigger只对应一个job
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(remindTaskDetail())
                                    .withIdentity("remindTaskCronTrigger").withDescription("触发器").withSchedule(csb).startNow().build();

        return cronTrigger;
    }

}
