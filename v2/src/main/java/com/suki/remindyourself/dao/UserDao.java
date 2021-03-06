package com.suki.remindyourself.dao;

import com.alibaba.fastjson.JSONObject;
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
        String sql = "select * from " + JDBCUtils.readProperties().getUserTable() + " where username = ? and password = ?" + ";";
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
        String sql = "select * from " + JDBCUtils.readProperties().getEventTable() + " where for_user_id = " + userId + ";";
        ArrayList<Event> eventList = JDBCUtils.selectEventFromMySql(sql);
        return eventList;
    }


    /**
     * 从数据库删除某些指定行的操作
     * User对象是为了获得u.getId()--->这是唯一id
     * jsonObject对象是为了获取establish_time、remind_time、content、state信息，用于删除时的条件判断
     * @param u
     * @param jsonObject
     */
    public void delete(User u, JSONObject jsonObject){
        String userId = u.getId() + "";
        String establishTime = (String) jsonObject.get("establish_time");
        String remindTime = (String) jsonObject.get("remind_time");
        String content = (String)jsonObject.get("content");
        String state = (String)jsonObject.get("state");

//        String sql = "delete from " + JDBCUtils.readProperties().getEventTable() +
//                " where for_user_id = " + userId + " and establish_time = " + "\'" + establishTime + "\'" + " and remind_time = " + "\'" + remindTime + "\'" +
//                " and content = " + "\'"  + content + "\'" +" and state = " + state;
        String sql = "delete from " + JDBCUtils.readProperties().getEventTable() + " where for_user_id = '" + userId +
                "' and establish_time = '" + establishTime + "' and remind_time = '" + remindTime + "' and content = '" + content + "'";
        System.out.println(sql);

        JDBCUtils.deleteFromEventTable(sql);

    }


    /**
     * 从数据库里更新[event]表的[state]从0变为1
     * @param u
     * @param jsonObject
     */
    public void update(User u, JSONObject jsonObject){
        String userId = u.getId() + "";
        String establishTime = (String) jsonObject.get("establish_time");
        String remindTime = (String) jsonObject.get("remind_time");
        String content = (String)jsonObject.get("content");
        String state = (String)jsonObject.get("state");

        String sql = "update event set state = '1' where for_user_id = '" + userId + "' and establish_time = '" + establishTime + "' and remind_time = '" + remindTime + "' and content = '" + content + "'";
        System.out.println(sql);
        JDBCUtils.updateFromEventTable(sql);

    }


    /**
     * 插入一条数据到[event]表中
     * @param u
     * @param jsonObject
     */
    public void insert(User u, JSONObject jsonObject){

        String sql = "insert into event (establish_time, remind_time, content, state, for_user_id) values(?, ?, ?, ?, ?)";

        JDBCUtils.insertFromEventTable(sql, u, jsonObject);
    }


    /**
     * 检查注册时传来的用户的用户名和邮箱是否存在于数据库
     * @param registerJson
     * @param str str="username"则检查username;  str="email"则检查email
     * @return 若str="username"则返回该用户名是否存在于数据库之中， 同理str="email"
     */
    public boolean checkRegisterUser(JSONObject registerJson, String str){
        if (str.equals("username")){
            String username = (String) registerJson.get("username");
            String sql = "select * from user where username = '" + username + "'";
            return JDBCUtils.register(sql);
        } else if (str.equals("email")){
            String email = (String) registerJson.get("email");
            String sql = "select * from user where email = '" + email + "'";
            return JDBCUtils.register(sql);
        } else {  // 这里可以为以后做扩充
            return true;
        }
    }


    /**
     * 这是在检查过用户名username和邮箱email没有出现在数据库之后才会执行的函数
     * 主要是把这个新注册的用户的信息存进数据库，也就是进行一个insert操作
     * @param jsonObject
     */
    public void registerInsert(JSONObject jsonObject){
        String sql = "insert into user (username, password, email) values(?, ?, ?)";
        JDBCUtils.registerInsert(jsonObject, sql);
    }

}
