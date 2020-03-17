package com.suki.remindyourself.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailUtils {

    @Autowired
    MailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    public void sendMail(String username, String userEmail, String establishTime, String remindTime, String remindContent) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(userEmail);
        //邮件主题
        message.setSubject("RemindYourself服务-待办事项提醒");
        //邮件内容
        message.setText(buildContent(username, establishTime, remindTime, remindContent));
        //发送邮件
        mailSender.send(message);

        log.info("邮件发送成功");
    }

    /**
     * 辅助：构建RemindYourself发送给用户的内容
     *
     * 内容格式：
     * ==RemindYourself服务==
     * xx，你好！
     * 建立时间：xx
     * 提醒时间：xx
     * 提醒内容：xx
     * @return
     */
    public String buildContent(String username, String establishTime, String remindTime, String remindContent) {
        return "==RemindYourself服务==\n" +
                username + ",你好!\n" +
                "建立时间:" + establishTime + "\n" +
                "提醒时间:" + remindTime + "\n" +
                "提醒内容:" + remindContent;
    }
}
