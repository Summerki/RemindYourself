package com.suki.remindyourself.dao;

import com.suki.remindyourself.aspect.DaoAspect;
import com.suki.remindyourself.exception.MySQLException;
import com.suki.remindyourself.po.Event;
import com.suki.remindyourself.po.User;
import com.suki.remindyourself.vo.sql.EventSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EventDao {

    @Autowired
    EventSQL eventSQL;

    @Autowired
    DaoAspect daoAspect;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = MySQLException.class)
    public List<Event> listEventsByForUserId(User user) {
        try {
            String sql = eventSQL.listEventsByForUserIdSQL;
            daoAspect.showSQLInfo(sql, new Object[]{user.getId()});
            List<Event> eventList = jdbcTemplate.query(sql, new Event(), user.getId());
            return eventList;
        } catch (Exception e) {
            throw new MySQLException("查询异常");
        }
    }
}
