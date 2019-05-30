[TOC]



## Remind Yourself网站项目

+ 学完`Java Web`之后的练手项目
+ 灵感来源
    + 现在总是会忘事
    + 使用过一段时间的主流`提醒事项`App，但是体验效果一般
    + 结合`Java Web`写了一个网页端的提醒事项网站
    + 通过`发送邮件`的方式按时提醒我具体的提醒事项

### 设计数据库结构

> 分为两张表，一张user表和一张event表

user表的结构为：

| id   | username | password | email |
| ---- | -------- | -------- | ----- |
|      |          |          |       |

event表的结构为：

| id   | establish_time | remind_time | content | state | for_user_id |
| ---- | -------------- | ----------- | ------- | ----- | ----------- |
|      |                |             |         |       |             |





### 临时

+ 虚拟目录名称：`/remindYourself`