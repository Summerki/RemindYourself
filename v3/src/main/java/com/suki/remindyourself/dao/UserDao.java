package com.suki.remindyourself.dao;

import com.suki.remindyourself.po.User;
import com.suki.remindyourself.vo.UserSQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * UserDao
 */
@Slf4j
@Repository
public class UserDao {

    @Autowired
    UserSQL userSQL;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserByUsernameAndPassword(String username, String password) {
        String sql = userSQL.getUserByUsernameAndPasswordSQL;
        User user = jdbcTemplate.queryForObject(sql, new User(), username, password);
        log.info("执行的sql语句:{} sql语句参数:{} 查询结果:{}", sql, new Object[]{username, password}, user);
        return user;
    }
}
