package com.suki.remindyourself.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suki.remindyourself.po.Event;
import com.suki.remindyourself.po.User;
import com.suki.remindyourself.quartz.CronUtils;
import com.suki.remindyourself.quartz.MyQuartzJob;
import com.suki.remindyourself.quartz.QuartzUtils;
import com.suki.remindyourself.service.EventService;
import com.suki.remindyourself.util.CheckArrUtils;
import com.suki.remindyourself.util.CheckUserAgentUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Autowired
    Scheduler scheduler;

    @Autowired
    QuartzUtils quartzUtils;

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


        Event event = eventService.getEvent(establishTime, remindTime, content);
        // 注意，我们将JobDetail的Identity设置为[name:event的id， group：username]
        JobDetail jobDetail = quartzUtils.getJobDetail(event.getId().toString(), u.getUsername(), MyQuartzJob.class,
                                                       u.getUsername(), u.getEmail(), establishTime, remindTime, content);
        Trigger trigger = quartzUtils.getTrigger(event.getId().toString(), u.getUsername(), CronUtils.fmtRemindTimeToCron(remindTime), jobDetail);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.info("调度失败");
            e.printStackTrace();
        }


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

        List<Event> unfinishStateEVents = eventService.getUnfinishStateEVents(getUnfinishStateList(markupJsonArr));
        for (Event event : unfinishStateEVents) {
            try {
                scheduler.pauseTrigger(quartzUtils.getTriggerKey(event.getId().toString(), u.getUsername()));
                scheduler.unscheduleJob(quartzUtils.getTriggerKey(event.getId().toString(), u.getUsername()));
                scheduler.deleteJob(quartzUtils.getJobKey(event.getId().toString(), u.getUsername()));
            } catch (SchedulerException e) {
                log.info("删除Quartz里的Job失败");
                e.printStackTrace();
            }
        }


        // 如果执行成功这个数组里面应该都是1
        int[] res = eventService.updateEventState(markupJsonArr, Integer.parseInt(u.getId().toString()));
        Map<String, Object> map = new HashMap<>();
        if (CheckArrUtils.checkArr(res, 1)) { // 判断数组结果是否为全1
            map.put("res", "success");
        } else {
            map.put("res", "fail");
        }

        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("deleteJsonArr")String deleteJsonStr,
                                      HttpSession session) {
        log.info("deleteStr {}", deleteJsonStr);
        JSONArray deleteJsonArr = (JSONArray) JSONArray.parse(deleteJsonStr);
        User u = (User) session.getAttribute("user");
        int[] res = eventService.removeEvents(deleteJsonArr, Integer.parseInt(u.getId().toString()));

        List<Event> unfinishStateEVents = eventService.getUnfinishStateEVents(getUnfinishStateList(deleteJsonArr));
        for (Event event : unfinishStateEVents) {
            try {
                scheduler.pauseTrigger(quartzUtils.getTriggerKey(event.getId().toString(), u.getUsername()));
                scheduler.unscheduleJob(quartzUtils.getTriggerKey(event.getId().toString(), u.getUsername()));
                scheduler.deleteJob(quartzUtils.getJobKey(event.getId().toString(), u.getUsername()));
            } catch (SchedulerException e) {
                log.info("删除Quartz里的Job失败");
                e.printStackTrace();
            }
        }

//        log.info("delete res {}", res);
        Map<String, Object> map = new HashMap<>();
        if (CheckArrUtils.checkArr(res, 1)) {
            map.put("res", "success");
        } else {
            map.put("res", "fail");
        }
        return map;
    }


    @RequestMapping("/index")
    public String logout(HttpSession session, HttpServletRequest request) {
        session.invalidate();
        if (CheckUserAgentUtils.checkAgentIsMobile(request.getHeader("user-agent"))) {
            return "mobile/index.m";
        }
        return "index";
    }


    /**
     * 辅助函数,得到所有state=0的List集合
     * [
     *  {establishTime:xx, remindTime:xx, content:xx, state:0/1},
     *  {establishTime:xx, remindTime:xx, content:xx, state:0/1},
     *   ...
     *  ]
     * @param jsonArray
     * @return
     */
    private List<Object[]> getUnfinishStateList(JSONArray jsonArray) {
        List<Object[]> unfinishStateList = new ArrayList<>();
        JSONObject jsonObject;
        Object[] args;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = (JSONObject) jsonArray.get(i);
            if (jsonObject.getInteger("state") == 0) {
                unfinishStateList.add(new Object[]{jsonObject.getString("establishTime"),
                                                    jsonObject.getString("remindTime"),
                                                    jsonObject.getString("content")});
            }
        }
        return unfinishStateList;
    }
}
