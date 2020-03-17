package com.suki.remindyourself.quartz;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class CronUtils {

    /**
     * 将remindTime（yyyy-MM-dd HH:mm:ss）格式化成cron表达式
     * @return
     */
    public static String fmtRemindTimeToCron(String remindTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        return sdf.format(fmtRemindTimeToDate(remindTime));
    }

    public static Date fmtRemindTimeToDate(String remindTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(remindTime);
        } catch (ParseException e) {
            log.info("转换时间格式失败");
            e.printStackTrace();
            return null;
        }
    }

}
