package com.suki.remindyourself.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuartzUtils {

    @Autowired
    Scheduler scheduler;


    public JobDetail getJobDetail(String jobName, String jobGroup, Class jobClass,
                                  String username, String userEmail,
                                  String establishTime, String remindTime, String remindContent) {
        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .usingJobData("username", username)
                .usingJobData("userEmail", userEmail)
                .usingJobData("establishTime", establishTime)
                .usingJobData("remindTime", remindTime)
                .usingJobData("remindContent", remindContent)
                .build();
    }

    public Trigger getTrigger(String triggerName, String triggerGroup, String cronExp, JobDetail jobDetail) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName, triggerGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                .forJob(jobDetail)
                .startNow()
                .build();
    }


    public JobKey getJobKey(String jobDetailName, String jobDetailGroup) {
        return JobKey.jobKey(jobDetailName, jobDetailGroup);
    }

    public TriggerKey getTriggerKey(String triggerName, String triggerGroup) {
        return TriggerKey.triggerKey(triggerName, triggerGroup);
    }

}
