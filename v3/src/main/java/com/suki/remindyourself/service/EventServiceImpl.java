package com.suki.remindyourself.service;

import com.suki.remindyourself.dao.EventDao;
import com.suki.remindyourself.po.Event;
import com.suki.remindyourself.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EventService接口实现类
 */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventDao eventDao;

    @Override
    public List<Event> listEventsByForUserId(User user) {
        return eventDao.listEventsByForUserId(user);
    }
}
