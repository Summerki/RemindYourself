package com.suki.remindyourself.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suki.remindyourself.vo.EventTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * po对象，对应数据库的event表结构
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Event implements RowMapper<Event> {

    private Long id;

    @DateTimeFormat(pattern = "yyyy:MM:dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy:MM:dd hh:mm:ss",timezone = "GMT+8")
    private Date establishTime;

    @DateTimeFormat(pattern = "yyyy:MM:dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy:MM:dd hh:mm:ss",timezone = "GMT+8")
    private Date remindTime;

    private String content;

    /**
     * 表征该事件是否已经完成。0为[进行中],1为[已完成]
     */
    private int state;
    /**
     * 关联User表的字段
     */
    private Long forUserId;


    @Autowired
    EventTable eventTable;
    @Override
    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getLong(eventTable.idColumn));
        event.setEstablishTime(resultSet.getTimestamp(eventTable.establishTimeColumn));
        event.setRemindTime(resultSet.getTimestamp(eventTable.remindTimeColumn));
        event.setContent(resultSet.getString(eventTable.contentColumn));
        event.setState(resultSet.getInt(eventTable.stateColumn));
        event.setForUserId(resultSet.getLong(eventTable.forUserIdColumn));
        return event;
    }
}