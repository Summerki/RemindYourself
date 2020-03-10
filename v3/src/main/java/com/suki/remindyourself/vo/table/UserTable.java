package com.suki.remindyourself.vo.table;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 将classpath:config/sql-config.properties注入
 * 表征的是对应数据库的字段的名称是什么，方便解耦
 */
@Component
@PropertySource(value = "classpath:/config/userTable.properties")
//@Data
@ToString
public class UserTable {

    /**
     * user表的名称
     */
    @Value("${user-table.tableName}")
    public String userTableName;

    // 下面是各个字段

    @Value("${user-table.id}")
    public String idColumn;

    @Value("${user-table.username}")
    public String usernameColumn;

    @Value("${user-table.password}")
    public String passwordColumn;

    @Value("${user-table.email}")
    public String emailColumn;

}
