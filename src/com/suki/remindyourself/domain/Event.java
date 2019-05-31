package com.suki.remindyourself.domain;


// 实体类：事件
public class Event {

    private int id;
    private String establishTime;
    private String remindTime;
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

    public String getEstablishTime() {
        return establishTime;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
