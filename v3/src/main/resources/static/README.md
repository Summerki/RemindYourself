说明:
+ `xxx.m.html`代表xxx页面的移动端视图;现在统一放在`移动端`文件夹内
+ `xxx.c.html`代表xxx页面的桌面端视图;现在统一放在`桌面端`文件夹内
+ 最外层的`xxx.html`为兼具桌面端和移动端的页面原型

更新思路:
+ 现在决定还是将`桌面端`和`移动端`页面分离出来
+ 通过springboot后台控制访问哪个终端的页面
+ 另外,关于页面CSS样式显示不出来的解决办法目前暂定为如下:(不是最好的解决办法,以后有了好的再说吧)
    + 现在将页面文件也放置在static文件夹下
    + 设置`spring.thymeleaf.prefix=classpath:/static/`
    + 这样就没事了
+ 关于`移动端`页面现在决定放置在`classpath:/static/mobile/`下面
    + `移动端`页面命名为`xxx.m.html`
    + `桌面端`页面命令为`xxx.html`
    
    
+ `@Slf4j`注解的使用：首先它是`Lombok`自带的注解；想要这个注解发挥作用，还必须要在IDEA的插件中下载`Lombok`插件；这样引入了`@Slf4j`注解的类里面自动会有一个`log`对象提供使用


+ 总结一下不用模板引擎开发springboot项目
    + 我们不再需要引入`thymeleaf`依赖；以及`spring.thymeleaf`的相关依赖我们都不必再关心了
    + 我们将页面文件以及其他的样式、JS文件等都放置在`resources`下的任意一个文件夹下
    + 我们通过配置`spring.resources.static-locations`、`spring.mvc.view.prefix`、`spring.mvc.view.suffix`来实现静态资源的跳转
        + 具体的做法和解释在本例中有详细解释
    + 最理想的情况是你的页面文件放在所有的样式文件的最外层，这样你的页面文件不用进行任何修改
    + 如果你的某个文件夹内还有页面文件，在你完成设计后要在`<head></head>`里的第一行加上`<base href="[你的context-path名称]/">`
        + 当然，这是一种投机取巧的做法，但是很有效对吗
        + 也要记得，你在设计时要把`<base href="[你的context-path名称]/">`注释掉，不然页面里的引用失效，无法预览页面