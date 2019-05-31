package com.suki.remindyourself.web.Servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suki.remindyourself.dao.UserDao;
import com.suki.remindyourself.domain.Event;
import com.suki.remindyourself.domain.User;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

// 主要用于第一次进入main.html页面后
// window.onload异步请求返回指定格式的json数据
// json指定数据格式为：{"user":User对象, "event":[{Event对象},{Event对象},...]}
@WebServlet("/mainServlet")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 获得session
        HttpSession session = req.getSession();
        // 1. 获得User对象
        User u = (User)session.getAttribute("user");
        // 2. 获取ArrayList<Event>集合
        UserDao userDao = new UserDao();
        ArrayList<Event> eventList = userDao.mainAjaxReturn(u);

        // just for test
        for (Event event : eventList){
            System.out.println(event);
        }
        System.out.println(u);

        // 构造json数据
        JSONObject json = new JSONObject();
        json.put("user", u);  // 这里的u肯定有值
        // eventList有可能有值有可能为null
        if (eventList == null){  // 因为有可能有些用户只是注册了账号但是没有建立提醒事项
            json.put("event", null);  // 格式这样event:[]
        }else {
            JSONArray jsonArray = new JSONArray();
            for (Event event : eventList){
                jsonArray.add(event);
            }
            json.put("event", jsonArray);
        }
        System.out.println(json.toJSONString());
        resp.getWriter().print(json);



    }
}
