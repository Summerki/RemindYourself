package com.suki.remindyourself.dao;

import com.suki.remindyourself.aspect.DaoAspect;
import com.suki.remindyourself.exception.MySQLException;
import com.suki.remindyourself.po.User;
import com.suki.remindyourself.vo.UserSQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserDao
 */
@Slf4j
@Repository
public class UserDao {

    @Autowired
    UserSQL userSQL;

    @Autowired
    DaoAspect daoAspect;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = MySQLException.class)
    public User getUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = userSQL.getUserByUsernameAndPasswordSQL;
            daoAspect.showSQLInfo(sql, new Object[]{username, password});
            User user = jdbcTemplate.queryForObject(sql, new User(), username, password);
            return user;
        } catch (Exception e) {
            throw new MySQLException("查询异常");
        }
    }
}
