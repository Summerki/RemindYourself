package com.suki.remindyourself.utils;

import com.suki.remindyourself.domain.PropertiesDomain;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// 工具类：操作JDBC
public class JDBCUtils {

    static PropertiesDomain p;
    // 读取配置文件properties
    // 返回一个PropertiesDomain对象
    private static PropertiesDomain readProperties(){
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


    // just for test
    public static void main(String[] args) {
        PropertiesDomain propertiesDomain = JDBCUtils.readProperties();
        JDBCUtils.getJDBCConnection(propertiesDomain);
    }

}
