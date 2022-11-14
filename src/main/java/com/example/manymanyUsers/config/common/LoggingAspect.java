package com.example.manymanyUsers.config.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * Controller Method
 * Request/Response Logging 설정
 */
@Slf4j
@Aspect
@Configuration
public class LoggingAspect {
    @Pointcut("execution(* com.example.manymanyUsers..*.*(..))")
    private void cut(){}

    /**
     * Pointcut에 의해 필터링 된 경로로 들어오는 경우 메서드 호출 직전 적용
     */
    @Before("cut()")
    public void onRequestLog(JoinPoint joinPoint){
        // 메서드 조회
        Method method = getMethod(joinPoint);
        log.info("REQUEST : {");
        log.info("  CLASS                   => {}", joinPoint.getTarget().getClass());
        log.info("  METHOD                  => {}", method.getName());

        Object[] args = joinPoint.getArgs();
        // 파라미터 출력
        if(args.length <= 0){
            log.info("  PARAMETER               => null");
        }else {
            for (Object arg : args) {
                if(arg == null){
                    log.info("  PARAMETER               => null");
                } else {
                    log.info("  PARAMETER TYPE/VALUE    => {}/{}", arg.getClass().getSimpleName(), arg);
                }
            }
        }
        log.info("}");
    }

    /**
     * Poincut에 의해 필터링 된 메서드 리턴 직후 적용
     */
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
        log.info("RESPONSE : {");

        if(returnObj == null){
            log.info("  RETURN                  => null");
        }else{
            log.info("  RETURN TYPE/VALUE       => {}/{}", returnObj.getClass().getSimpleName(), returnObj);
        }
        log.info("}");
    }

    /**
     * 메서드 정보 조회
     */
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
