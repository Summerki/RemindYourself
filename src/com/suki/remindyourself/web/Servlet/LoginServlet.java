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
;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
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

        // 如果返回的u不为null，将其存到session之中为其他请求使用
        HttpSession session = req.getSession();
        if (u != null){
            session.setAttribute("user", u);
        }

        JSONObject json = new JSONObject();
        json.put("user", u);
        resp.getWriter().print(json);






    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
