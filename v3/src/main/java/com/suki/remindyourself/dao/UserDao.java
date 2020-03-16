package com.suki.remindyourself.dao;

import com.suki.remindyourself.aspect.DaoAspect;
import com.suki.remindyourself.exception.MySQLException;
import com.suki.remindyourself.po.User;
import com.suki.remindyourself.vo.sql.UserSQL;
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


    public User getUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = userSQL.getUserByUsernameAndPasswordSQL;
            daoAspect.showSQLInfo(sql, new Object[]{username, password});
            User user = jdbcTemplate.queryForObject(sql, new User(), username, password);
            return user;
        } catch (Exception e) {
            return null;
        }
    }


    public boolean getUserByUsername(String username) {
        try {
            String sql = userSQL.getUserByUsername;
            daoAspect.showSQLInfo(sql, new Object[]{username});
            User user = jdbcTemplate.queryForObject(sql, new User(), username);
            if (user != null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
    }

    public boolean getUserByEmail(String email) {
        try {
            String sql = userSQL.getUserByEmail;
            daoAspect.showSQLInfo(sql, new Object[]{email});
            User user = jdbcTemplate.queryForObject(sql, new User(), email);
            if (user != null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
    }

    @Transactional(rollbackFor = MySQLException.class)
    public int saveUser(String username, String password, String email) {
        try {
            String sql = userSQL.saveUser;
            daoAspect.showSQLInfo(sql, new Object[]{username, password, email});
            int res = jdbcTemplate.update(sql, new Object[]{username, password, email});
            return res;
        } catch (Exception e) {
            throw new MySQLException("新增用户异常");
        }
    }
}
