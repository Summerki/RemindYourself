package com.suki.remindyourself.web.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suki.remindyourself.dao.UserDao;
import com.suki.remindyourself.domain.User;
import com.sun.javafx.collections.MappingChange;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置req编码
        req.setCharacterEncoding("utf-8");
        // 获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        // 封装成User对象
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        // 调用UserDao的方法
        UserDao userDao = new UserDao();
        User u = userDao.checkLogin(loginUser);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        if (u != null){
            // 返回一个json对象
            map.put("result", "success");
        }else{
            map.put("result", "fail");
        }
        // 转为json数据
        String json = mapper.writeValueAsString(map);
        System.out.println(json);

        // 设置response编码方式
        resp.setContentType("application/json;charset=utf-8");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
