package com.suki.remindyourself.domain;

// 实体类：配置文件实体类
public class PropertiesDomain {
    private String driverClassName;
    private String url;
    private String dataBase;
    private String userTable;
    private String eventTable;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "PropertiesDomain{" +
                "driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", dataBase='" + dataBase + '\'' +
                ", userTable='" + userTable + '\'' +
                ", eventTable='" + eventTable + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUserTable() {
        return userTable;
    }

    public void setUserTable(String userTable) {
        this.userTable = userTable;
    }

    public String getEventTable() {
        return eventTable;
    }

    public void setEventTable(String eventTable) {
        this.eventTable = eventTable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
