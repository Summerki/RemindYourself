package com.suki.remindyourself.vo.sql;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:/config/eventSql.properties")
@Component
@ToString
public class EventSQL {

    @Value("${sql.listEventsByForUserId}")
    public String listEventsByForUserIdSQL;

    @Value("${sql.saveEvent}")
    public String saveEventSQL;

}
