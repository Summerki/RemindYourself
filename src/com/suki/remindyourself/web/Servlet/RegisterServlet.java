package com.suki.remindyourself.web.Servlet;

import com.alibaba.fastjson.JSONObject;
import com.suki.remindyourself.dao.UserDao;
import com.suki.remindyourself.domain.User;
import com.suki.remindyourself.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 获取前台传来的json数据
        // 格式为：{"username":"summerki","password":"591593076","email":"591593076@qq.com"}
        String registerJsonStr = req.getParameter("registerJson");
        System.out.println(registerJsonStr);
        JSONObject registerJson = JSONObject.parseObject(registerJsonStr);

        // 分别判定用户名和邮箱有没有重复的
        // 定义：返回false-->未查到结果（可以注册）  返回true-->查到结果（应当阻止注册）
        UserDao userDao = new UserDao();
        boolean checkUsername = userDao.checkRegisterUser(registerJson, "username");
        boolean checkEmail = userDao.checkRegisterUser(registerJson, "email");


        // 如果 checkUsername 和 checkEmail 都为false，则应该将这一条数据插入到user表中
        if (!(checkUsername || checkEmail)){
            userDao.registerInsert(registerJson);
        }

        // 定义再次验证后传回给浏览器的json格式为：{"checkUsername":0/1, "checkEmail":0/1}
        // 0代表false  1代表true
        JSONObject retJson = new JSONObject();
        if (checkUsername){
            retJson.put("checkUsername", 1);
        } else {
            retJson.put("checkUsername", 0);
        }

        if (checkEmail){
            retJson.put("checkEmail", 1);
        } else {
            retJson.put("checkEmail", 0);
        }

        resp.getWriter().print(retJson);
    }
}
