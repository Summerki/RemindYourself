package com.suki.remindyourself.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suki.remindyourself.dao.EventDao;
import com.suki.remindyourself.po.Event;
import com.suki.remindyourself.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public int saveEvent(String establishTime, String remindTime, String content, Integer state, Integer forUserId) {
        int res = eventDao.saveEvent(establishTime, remindTime, content, state, forUserId);
        return res;
    }

    @Override
    public int[] updateEventState(JSONArray jsonArray, Integer forUserId) {
        // 利用jsonArray和forUserId构造批量更新sql的数组
        List<Object[]> sqlArgList = new ArrayList<>();
        JSONObject jsonObject;
        Object[] sqlArg;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = (JSONObject) jsonArray.get(i);
            sqlArg = new Object[]{jsonObject.getString("establishTime"),
                                  jsonObject.getString("remindTime"),
                                  jsonObject.getString("content"),
                                  forUserId};
            sqlArgList.add(sqlArg);
        }
        int[] res = eventDao.updateEventState(sqlArgList);
        return res;
    }


}
