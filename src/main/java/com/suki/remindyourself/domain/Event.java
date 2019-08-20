package com.suki.remindyourself.domain;


// 实体类：事件
public class Event {

    private int id;
    private String establishTime;
    private String remindTime;
    private String content;
    private String state;
    private String forUserId;

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

    public void setState(String state) {
        this.state = state;
    }

    public void setForUserId(String forUserId) {
        this.forUserId = forUserId;
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

    public String getState() {
        return state;
    }

    public String getForUserId() {
        return forUserId;
    }

}
