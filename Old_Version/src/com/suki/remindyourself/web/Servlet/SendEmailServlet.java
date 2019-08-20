package com.suki.remindyourself.web.Servlet;

import com.suki.remindyourself.domain.Event;
import com.suki.remindyourself.domain.User;
import com.suki.remindyourself.utils.JDBCUtils;
import com.suki.remindyourself.utils.MailUtils;
import sun.security.util.AuthResources_it;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@WebServlet(loadOnStartup = 0, urlPatterns = "/sendEmailServlet")
public class SendEmailServlet extends HttpServlet {
    static ArrayList<Event> eventList;
    static Event event;
    static String sqlForEvent = "select * from event";  // 定义遍历event数据库的sql语句
    static String nowTimeStr;  // 当前时间字符串
    static User SendEmailUser;  // 找到的要发送邮件的用户
    static String sendEmailStr;  // 发送邮件的内容
    static String changeStateSql;  // 定义发送完邮件之后event的状态由0变为1的sql语句
    static long threadSleepTime = 20000;  // 线程休息的时间

    @Override
    public void init() throws ServletException {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // 获取此时时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    long now = System.currentTimeMillis();
                    nowTimeStr = sdf.format(now);
                    System.out.println(nowTimeStr);

                    // 判断数据库里的时间是否和当前时间是否相等，然后决定是否发送邮件
                    eventList = JDBCUtils.findAllEvent(sqlForEvent);
                    System.out.println("size=====>" + eventList.size());
                    for (int i = 0 ; i < eventList.size(); i++){
                        event = eventList.get(i);
                        System.out.println("====>" + event);
                        if (event.getState().equals("0") && nowTimeStr.equals(event.getRemindTime())){
                            String sqlForSendUser = "select * from user where id = '" + event.getForUserId() + "'";
                            System.out.println(sqlForSendUser);
                            SendEmailUser = JDBCUtils.findSendEmailUser(sqlForSendUser);
                            sendEmailStr = "=====Summerki的女仆提醒您=====<br>" +
                                    "提醒事项:" + event.getContent() + "<br>" +
                                    "创建时间:" + event.getEstablishTime() + "<br>" +
                                    "提醒时间:" + event.getRemindTime() + "<br>";
                            MailUtils.sendMail(SendEmailUser.getEmail(), sendEmailStr, "RemindYourself小站发来提醒");

                            // 弄完之后还要将该事件的状态变为已完成
                            changeStateSql = "update event set state = '1' where for_user_id = '" + event.getForUserId() + "' and establish_time = '" + event.getEstablishTime() + "' and remind_time = '" + event.getRemindTime() + "' and content = '" + event.getContent() + "'";
                            JDBCUtils.updateFromEventTable(changeStateSql);
                        }
                    }

                    // 遇到了一个难受的bug，eventList每次添加后没有清空，所以size会不断增大，而且邮件会发送得越来越频繁，在这里clear一下就好了
                    eventList.clear();

                    // 延时操作
                    try {
                        System.out.println("===>来到了延时操作这里");
                        Thread.sleep(threadSleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
