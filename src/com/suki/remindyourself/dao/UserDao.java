package com.suki.remindyourself.dao;

import com.suki.remindyourself.domain.Event;
import com.suki.remindyourself.domain.User;
import com.suki.remindyourself.utils.JDBCUtils;

import java.util.ArrayList;

// DAO层，专注于数据库基本操作
public class UserDao {
    User u;


    /**
     * 接收LoginServlet传来的User对象，判断数据库中有无此对象
     * @param loginUser LoginServlet传来的User对象
     * @return  返回查询到的User对象，有则返回User，无则返回null
     */
    public User checkLogin(User loginUser){
        String sql = "select * from " + JDBCUtils.readProperties().getUserTable() + " where username = ? and password = ?";
        u = JDBCUtils.selectFromMySql(sql, loginUser);
        return u;
    }

    /**
     * 根据在MainServlet里session获得的User对象根据其getId()找到user表中对应的id值
     * 再根据这个id值查找event表中for_user_id字段相同的行
     * 将查找到的每一行都封装成一个Event对象再放到ArrayList之中返回
     * @param u
     * @return
     */
    public ArrayList<Event> mainAjaxReturn(User u){
        String userId = u.getId() + "";
        String sql = "select * from " + JDBCUtils.readProperties().getEventTable() + " where for_user_id = " + userId;
        ArrayList<Event> eventList = JDBCUtils.selectEventFromMySql(sql);
        return eventList;
    }

}
