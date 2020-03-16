package com.suki.remindyourself.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Dao层操作的切面：记录每次每个函数操作数据库的执行时间
 */
@Aspect
@Component
@Slf4j
public class DaoAspect {

    // 切点
    @Pointcut("execution(* com.suki.remindyourself.dao.*.*(..))")
    public void log(){}

    ThreadLocal<Long> startTime = new ThreadLocal<>(); // 使用ThreadLocal<T>可以保证让每个线程独占一份资源
    @Around("log()")
    public Object doAround(ProceedingJoinPoint pjp) {
        startTime.set(System.currentTimeMillis());
        Object obj;
        try {
            log.info("====={}方法开始执行=====", pjp.getSignature().getName());
            obj = pjp.proceed(); // 执行目标函数
            log.info("{}方法本次请求的返回值{}", pjp.getSignature().getName(), obj);
            log.info("====={}方法执行结束=====", pjp.getSignature().getName());
        } catch (Throwable throwable) {
            obj = throwable.toString();
        }
        return obj;
    }

    /**
     * 辅助函数，展示sql语句和用到的参数列表
     * @param sql
     * @param args
     */
    public void showSQLInfo(String sql, Object[] args) {
        log.info("执行的sql语句:{},执行的sql语句的参数为:{}", sql, args);
    }

    /**
     * 辅助函数
     * @param sql
     * @param sqlArgList
     */
    public void showSQLInfo(String sql, List<Object[]> sqlArgList) {
        log.info("执行的sql语句:{},执行的sql语句的参数为:{}", sql, sqlArgList);
    }
}
