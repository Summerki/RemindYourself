package com.suki.remindyourself.utils;

import com.suki.remindyourself.domain.Event;
import com.suki.remindyourself.domain.PropertiesDomain;
import com.suki.remindyourself.domain.User;
import sun.plugin2.message.EventMessage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.Properties;

// 工具类：操作JDBC
public class JDBCUtils {

    static PropertiesDomain p;
    // 读取配置文件properties
    // 返回一个PropertiesDomain对象
    public static PropertiesDomain readProperties(){
        Properties properties = new Properties();
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("JDBC.properties");
        try {
            properties.load(is);
            p = new PropertiesDomain();
            p.setDriverClassName(properties.getProperty("driverClassName"));
            p.setUrl(properties.getProperty("url"));
            p.setDataBase(properties.getProperty("dataBase"));
            p.setUserTable(properties.getProperty("userTable"));
            p.setEventTable(properties.getProperty("eventTable"));
            p.setUsername(properties.getProperty("username"));
            p.setPassword(properties.getProperty("password"));
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("JDBC.properties设置有误");
            return null;  // 返回null
        }

    }


    static Connection conn = null;
    // 获得JDBC连接
    private static void getJDBCConnection(PropertiesDomain propertiesDomain){
        try {
            Class.forName(propertiesDomain.getDriverClassName());
            conn = DriverManager.getConnection(propertiesDomain.getUrl() + "/" + propertiesDomain.getDataBase(), propertiesDomain.getUsername(), p.getPassword());
            System.out.println("连接数据库成功");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("连接数据库失败");
        }
    }


    static PreparedStatement ps = null;
    static ResultSet rs = null;
    static User u;
    // 专用于select * from user where username = ? and password = ? 的场合
    // 通过LoginServlet传来的User对象和Mysql数据库进行比对返回一个User对象
    public static User selectFromMySql(String sql, User user){
        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 先获得数据库连接
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, user.getUsername());
            ps.setObject(2, user.getPassword());
            rs = ps.executeQuery();
            while (rs.next()){
                u = new User();
                u.setId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEmail(rs.getString(4));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("UserDao类的selectFromMySql方法出现异常");
        }
        return null;  // 代表没有找到结果则返回null
    }


    static Event event;
    static ArrayList<Event> eventList;
    // 根据指定sql语句找到符合条件的event表中的行
    public static ArrayList<Event> selectEventFromMySql(String sql){
        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            eventList = new ArrayList<>();
            while (rs.next()){
                event = new Event();
                event.setId(rs.getInt(1));
                event.setEstablishTime(rs.getString(2));
                event.setRemindTime(rs.getString(3));
                event.setContent(rs.getString(4));
                event.setState(rs.getInt(5));
                event.setForUserId(rs.getInt(6));
                eventList.add(event);
            }
            return eventList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("UserDao类的selectEventFromMySql方法出现异常");
            return null;  // 没有找到对应的event事件则返回null
        }
    }


    // just for test
    public static void main(String[] args) {
        PropertiesDomain propertiesDomain = JDBCUtils.readProperties();
        JDBCUtils.getJDBCConnection(propertiesDomain);
    }

}
