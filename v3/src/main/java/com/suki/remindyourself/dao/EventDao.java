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

import java.util.ArrayList;
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

    @Transactional(rollbackFor = MySQLException.class)
    public int saveEvent(String establishTime, String remindTime, String content, Integer state, Integer forUserId) {
        try {
            String sql = eventSQL.saveEventSQL;
            Object[] args = new Object[]{establishTime, remindTime, content, state, forUserId};
            daoAspect.showSQLInfo(sql, args);
            int res = jdbcTemplate.update(sql, args);
            return res;
        } catch (Exception e) {
            throw new MySQLException("新增数据异常");
        }
    }

    @Transactional(rollbackFor = MySQLException.class)
    public int[] updateEventState(List<Object[]> sqlArgList) {
        try {
            String sql = eventSQL.updateEventState;
            daoAspect.showSQLInfo(sql, sqlArgList);
            int[] res = jdbcTemplate.batchUpdate(sql, sqlArgList);
            return res;
        } catch (Exception e) {
            throw new MySQLException("批量更新出错");
        }
    }

    @Transactional(rollbackFor = MySQLException.class)
    public int[] removeEvents(List<Object[]> sqlArgList) {
        try {
            String sql = eventSQL.removeEvents;
            daoAspect.showSQLInfo(sql, sqlArgList);
            int[] res = jdbcTemplate.batchUpdate(sql, sqlArgList);
            return res;
        } catch (Exception e) {
            throw new MySQLException("批量删除出错");
        }
    }

    public Event getEvent(String establishTime, String remindTime, String content) {
        try {
            String sql = eventSQL.getEvent;
            daoAspect.showSQLInfo(sql, new Object[]{establishTime, remindTime, content});
            Event event = jdbcTemplate.queryForObject(sql, new Event(), establishTime, remindTime, content);
            return event;
        } catch (Exception e) {
            throw new MySQLException("查询提醒事件异常");
        }
    }


    public List<Event> listUnfinishStateEvents(List<Object[]> unfinishStateList) {
        List<Event> eventList = new ArrayList<>();
        Event event;
        try {
            String sql = eventSQL.getEvent;
            daoAspect.showSQLInfo(sql, unfinishStateList);
            for (Object[] arg : unfinishStateList) {
                event = jdbcTemplate.queryForObject(sql, new Event(), arg);
                eventList.add(event);
            }
        } catch (Exception e) {
            throw new MySQLException("查询事件集合异常");
        }
        return eventList;
    }

    public int updateEventStateTo1(String establishTime, String remindTime, String content) {
        try {
            String sql = eventSQL.updateEventStateTo1;
            daoAspect.showSQLInfo(sql, new Object[]{establishTime, remindTime, content});
            int res = jdbcTemplate.update(sql, establishTime, remindTime, content);
            return res;
        } catch (Exception e) {
            throw new MySQLException("更新事件失败");
        }
    }
}


