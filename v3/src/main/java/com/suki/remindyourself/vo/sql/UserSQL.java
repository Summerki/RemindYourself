package com.suki.remindyourself.vo.sql;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 从 userSql.properties 中注入查询 User 相关的SQL语句
 */
@PropertySource("classpath:/config/userSql.properties")
@Component
@ToString
public class UserSQL {

    /**
     * 根据 username 和 password 查询user
     */
    @Value("${sql.getUserByUsernameAndPassword}")
    public String getUserByUsernameAndPasswordSQL;

    @Value("${sql.getUserByUsername}")
    public String getUserByUsername;

    @Value("${sql.getUserByEmail}")
    public String getUserByEmail;

    @Value("${sql.saveUser}")
    public String saveUser;
}