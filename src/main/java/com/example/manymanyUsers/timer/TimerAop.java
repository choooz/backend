package com.example.manymanyUsers.timer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAop {
    @Pointcut("@annotation(com.example.manymanyUsers.timer.Timer)")
    private void enableTimer() {}

    @Around("enableTimer()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println("total time is : " + stopWatch.getTotalTimeMillis());

        return result;
    }
}