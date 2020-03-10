package com.suki.remindyourself.vo.table;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:/config/eventTable.properties")
//@Data
@ToString
public class EventTable {
    @Value("${event-table.tableName}")
    public String eventTableName;

    @Value("${event-table.id}")
    public String idColumn;

    @Value("${event-table.establish-time}")
    public String establishTimeColumn;

    @Value("${event-table.remind-time}")
    public String remindTimeColumn;

    @Value("${event-table.content}")
    public String contentColumn;

    @Value("${event-table.state}")
    public String stateColumn;

    @Value("${event-table.for-user-id}")
    public String forUserIdColumn;
}
