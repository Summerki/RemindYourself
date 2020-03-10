package com.suki.remindyourself.service;

import com.suki.remindyourself.po.Event;
import com.suki.remindyourself.po.User;

import java.util.List;

/**
 * EventService接口
 */
public interface EventService {

    List<Event> listEventsByForUserId(User user);

}
