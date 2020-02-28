package com.suki.remindyourself.web.Servlet;

import com.alibaba.fastjson.JSON;
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

// main.html进行删除操作时，传到服务器的json数据包含要删除的行的信息
@WebServlet("/deleteServlet")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 通过session获取User对象
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");

        // 接收来自前端返回的json数据
        // deleteJson数据格式为: [{"establish_time":"2019-07-31 10:30","remind_time":"2019-05-12 10:30","content":"特特特特特特","state":"1"},{},{}...]
        JSONObject jsonObject = new JSONObject();
        UserDao userDao = new UserDao();
        String json = req.getParameter("deleteJson");
        System.out.println(json);
        JSONArray jsonArray = JSONArray.parseArray(json);  // 将str化为JsonArray对象
        for (Object o : jsonArray){
            jsonObject = (JSONObject)o;  // 再把JSonArray里面的每一个对象化为JsonObject对象
            System.out.println(jsonObject.get("establish_time"));
            // 对每一条delete记录给数据库删除操作
            userDao.delete(u, jsonObject);
        }


    }
}
