package com.suki.remindyourself.domain;

import java.util.Date;

// 实体类：事件
public class Event {

    private int id;
    private Date establishTime;
    private Date remindTime;
    private String content;
    private int state;
    private int forUserId;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", establishTime=" + establishTime +
                ", remindTime=" + remindTime +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", forUserId=" + forUserId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getForUserId() {
        return forUserId;
    }

    public void setForUserId(int forUserId) {
        this.forUserId = forUserId;
    }
}
