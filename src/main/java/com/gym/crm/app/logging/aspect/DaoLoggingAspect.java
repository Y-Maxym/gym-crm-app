package com.gym.crm.app.logging.aspect;

import com.gym.crm.app.logging.LogHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.gym.crm.app.util.Constants.DEBUG_REPOSITORY_EXCEPTION;
import static com.gym.crm.app.util.Constants.DEBUG_REPOSITORY_INPUT;
import static com.gym.crm.app.util.Constants.DEBUG_REPOSITORY_RESULT;
import static com.gym.crm.app.util.Constants.INFO_REPOSITORY_EXCEPTION;
import static com.gym.crm.app.util.Constants.INFO_REPOSITORY_INPUT;
import static com.gym.crm.app.util.Constants.INFO_REPOSITORY_RESULT;

@Slf4j
@Aspect
@Component
@Setter(onMethod_ = @Autowired)
public class DaoLoggingAspect {

    private LogHandler utils;

    @Pointcut("execution(* com.gym.crm.app.repository..*(..))")
    public void daoMethods() {
    }

    @Before("daoMethods() && args(*)")
    public void logBefore(JoinPoint joinPoint) {
        utils.logBefore(joinPoint, INFO_REPOSITORY_INPUT, DEBUG_REPOSITORY_INPUT);
    }

    @AfterReturning(pointcut = "daoMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        utils.logAfterReturning(joinPoint, result, INFO_REPOSITORY_RESULT, DEBUG_REPOSITORY_RESULT);
    }

    @AfterThrowing(pointcut = "daoMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        utils.logAfterThrowing(joinPoint, ex, INFO_REPOSITORY_EXCEPTION, DEBUG_REPOSITORY_EXCEPTION);
    }
}