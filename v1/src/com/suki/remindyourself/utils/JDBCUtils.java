package com.suki.remindyourself.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suki.remindyourself.domain.Event;
import com.suki.remindyourself.domain.PropertiesDomain;
import com.suki.remindyourself.domain.User;
import sun.plugin2.message.EventMessage;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.awt.geom.FlatteningPathIterator;
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
            conn = DriverManager.getConnection(propertiesDomain.getUrl() + "/" + propertiesDomain.getDataBase() + "?useUnicode=true&characterEncoding=UTF-8", propertiesDomain.getUsername(), p.getPassword());
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
//        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接
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
                event.setState(rs.getString(5));
                event.setForUserId(rs.getString(6));
                eventList.add(event);
            }
            return eventList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("UserDao类的selectEventFromMySql方法出现异常");
            return null;  // 没有找到对应的event事件则返回null
        }
    }


    // 根据指定sql语句删除event数据库中指定的行
    public static void deleteFromEventTable(String sql){
        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接

        try {
            ps = conn.prepareStatement(sql);
            int updateCount = ps.executeUpdate();  // 返回更新的行数
            System.out.println("返回进行删除操作的行数--->" + updateCount);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBCUtils类的deleteFromEventTable函数出现错误");
        }

    }


    // 根据指定sql语句更新[event]表数据
    public static void updateFromEventTable(String sql){

        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接

        try {
            ps = conn.prepareStatement(sql);
            int updateCount = ps.executeUpdate();
            System.out.println("返回执行更新操作的行数--->" + updateCount);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBCUtils类的updateFromEventTable函数出现了错误");
        }
    }


    // 插入一条数据到[event]之中
    public static void insertFromEventTable(String sql, User u, JSONObject jsonObject){
        String userId = u.getId() + "";
        String establishTime = (String) jsonObject.get("establish_time");
        String remindTime = (String) jsonObject.get("remind_time");
        String content = (String)jsonObject.get("content");
        String state = (String)jsonObject.get("state");

        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, establishTime);
            ps.setObject(2, remindTime);
            ps.setObject(3, content);
            ps.setObject(4, state);
            ps.setObject(5, userId);
            int insertCount = ps.executeUpdate();
            System.out.println("返回执行插入操作的行数-->" + insertCount);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBCUtils类的insertFromEventTable方法出现了异常");
        }

    }


    // 用户注册时操作数据库
    public static boolean register(String sql){
        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (!rs.next()){  // 未查到结果
                return false;  // 返回false代表未查到结果
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBCUtils类的register方法出现异常");
            return true;  // 我认为系统出现异常应当先阻止用户注册，所以这里返回true
        }
    }

    // 用于注册成功后将该用户信息插入到数据库之中
    public static void registerInsert(JSONObject jsonObject, String sql){
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");
        String email = (String) jsonObject.get("email");

        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, username);
            ps.setObject(2, password);
            ps.setObject(3, email);
            int registerInsertCount = ps.executeUpdate();
            System.out.println("执行用户注册完成之后返回插入行数-->" + registerInsertCount);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBCUtils类的registerInsert方法出现异常");
        }

    }


    // 在 [发送邮件Servlet] 时每隔20s遍历数据库找到所有事件集合
    static Event forSendEmailEvent;
    static ArrayList<Event> forSendEmailEventList = new ArrayList<>();
    public static ArrayList<Event> findAllEvent(String sql){
        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                forSendEmailEvent = new Event();
                forSendEmailEvent.setId(rs.getInt(1));
                forSendEmailEvent.setEstablishTime(rs.getString(2));
                forSendEmailEvent.setRemindTime(rs.getString(3));
                forSendEmailEvent.setContent(rs.getString(4));
                forSendEmailEvent.setState(rs.getString(5));
                forSendEmailEvent.setForUserId(rs.getString(6));
                forSendEmailEventList.add(forSendEmailEvent);
            }
            return forSendEmailEventList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBCUtils类的findAllEvent方法出错了");
            return null;
        }

    }


    // 在 [发送邮件Servlet] 时每隔20s遍历数据库找到所有的用户集合
    static User forSendEmailUser;
    public static User findSendEmailUser(String sql){
        JDBCUtils.getJDBCConnection(JDBCUtils.readProperties());  // 获得Mysql连接
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                forSendEmailUser = new User();
                forSendEmailUser.setId(rs.getInt(1));
                forSendEmailUser.setUsername(rs.getString(2));
                forSendEmailUser.setPassword(rs.getString(3));
                forSendEmailUser.setEmail(rs.getString(4));
            }
            return forSendEmailUser;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBCUtils类的findSendEmailUser方法出现了问题");
            return null;
        }

    }


    // just for test
    public static void main(String[] args) {
        PropertiesDomain propertiesDomain = JDBCUtils.readProperties();
        JDBCUtils.getJDBCConnection(propertiesDomain);
    }

}
