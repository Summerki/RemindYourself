package com.suki.remindyourself.web.Servlet;

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

@WebServlet("/insertServlet")
public class InsertServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 获取session中的User对象
        HttpSession session = req.getSession();
        User u = (User)session.getAttribute("user");

        // 获取前端异步请求的insertJson数据
        String insertJson = req.getParameter("insertJson");
        System.out.println(insertJson);
        JSONObject jsonObject = JSONObject.parseObject(insertJson);
        UserDao userDao = new UserDao();
        userDao.insert(u, jsonObject);
    }
}
