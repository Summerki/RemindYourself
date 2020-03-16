package com.suki.remindyourself.service;

import com.alibaba.fastjson.JSONArray;
import com.suki.remindyourself.po.Event;
import com.suki.remindyourself.po.User;

import java.util.List;

/**
 * EventService接口
 */
public interface EventService {

    List<Event> listEventsByForUserId(User user);

    int saveEvent(String establishTime, String remindTime, String content, Integer state, Integer forUserId);

    int[] updateEventState(JSONArray jsonArray, Integer forUserId);
}
