package com.suki.remindyourself.dao;

import com.suki.remindyourself.domain.User;
import com.suki.remindyourself.utils.JDBCUtils;

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

}
