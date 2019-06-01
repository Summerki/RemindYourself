package com.suki.remindyourself.web.Servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suki.remindyourself.dao.UserDao;
import com.suki.remindyourself.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 进行[事件状态]更新操作的Servlet
@WebServlet("/updateServlet")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 获取对象User
        HttpSession session = req.getSession();
        User u = (User)session.getAttribute("user");

        // 获取updateJson数据
        // updateJson数据格式：[{"establish_time":"2019-05-31 10:30","remind_time":"2019-06-01 12:00","content":"提醒事项Demo","state":"0"},{},{}...]
        String updateJson = req.getParameter("updateJson");
        System.out.println(updateJson);
        // 目的是把[updateJson]里的数据的state在数据库里由0改为1即可
        JSONObject jsonObject = new JSONObject();
        UserDao userDao = new UserDao();
        JSONArray jsonArray = JSONArray.parseArray(updateJson);
        for (Object o : jsonArray){
            jsonObject = (JSONObject)o;
            System.out.println(jsonObject.get("establish_time"));
            userDao.update(u, jsonObject);
        }
    }
}
