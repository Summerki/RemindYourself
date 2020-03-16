package com.suki.remindyourself.web;

import com.alibaba.fastjson.JSONArray;
import com.suki.remindyourself.po.Event;
import com.suki.remindyourself.po.User;
import com.suki.remindyourself.service.EventService;
import com.suki.remindyourself.util.CheckArr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对应 main.html 的 controller
 */
@Controller
@Slf4j
public class MainController {

    @Autowired
    EventService eventService;

    /**
     * 返回json数据，格式如下：
     * {
     *     'user':user,
     *     'events':[event1, event2, ...]
     * }
     * @param session
     * @return
     */
    @RequestMapping("/getUserAndEvents")
    @ResponseBody
    public Map<String, Object> getUserAndEvents(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        map.put("user", user);
        List<Event> eventList = eventService.listEventsByForUserId(user);
        map.put("events", eventList);
        return map;
    }

    /**
     * 接收到的json格式为：
     * {establishTime: getFormatDate(),
     * remindTime: fields.newNotifyDate,
     * content: fields.newNotify,
     * state: 0};
     *
     * 返回的json格式：
     * {
     *     res:0/1
     * }
     */
    @RequestMapping("/buildNewNotify")
    @ResponseBody
    public Map<String, Object> buildNewNotify(@RequestParam("establishTime") String establishTime,
                                              @RequestParam("remindTime") String remindTime,
                                              @RequestParam("content") String content,
                                              @RequestParam("state") Integer state,
                                              HttpSession session) {

        User u = (User) session.getAttribute("user");
        log.info("establishTime {}, remindTime {}, content {}, state {}, for-user-id {}", establishTime, remindTime, content, state, u.getId());
        int res = eventService.saveEvent(establishTime, remindTime, content, state, Integer.parseInt(u.getId().toString()));
        Map<String, Object> map = new HashMap<>();
        map.put("res", res);
        return map;
    }

    /**
     * 将用户的选择项标记为已完成
     * 前端json格式：(是一个数组字符串)
     * {
     *     markupJsonArr: [
     *          {establishTime:xx, remindTime:xx, content:xx, state:0/1},
     *          {establishTime:xx, remindTime:xx, content:xx, state:0/1},
     *          ...
     *     ]
     * }
     *
     * 返回json格式：
     * {
     *     res:'success'/'fail'
     * }
     * @return
     */
    @RequestMapping("/markup")
    @ResponseBody
    public Map<String, Object> markup(@RequestParam("markupJsonArr") String markupStr, HttpSession session) {
        log.info("markupJsonArr = {}",markupStr);
        JSONArray markupJsonArr = (JSONArray) JSONArray.parse(markupStr);
        User u =  (User) session.getAttribute("user");
        // 如果执行成功这个数组里面应该都是1
        int[] res = eventService.updateEventState(markupJsonArr, Integer.parseInt(u.getId().toString()));
        log.info("res {}", res);
        Map<String, Object> map = new HashMap<>();
        if (CheckArr.checkArr(res, 1)) { // 判断数组结果是否为全1
            map.put("res", "success");
        } else {
            map.put("res", "fail");
        }
        return map;
    }

}
