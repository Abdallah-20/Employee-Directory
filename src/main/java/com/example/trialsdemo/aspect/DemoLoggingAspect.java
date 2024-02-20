package com.example.trialsdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {

    // Setup Logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    // Setup Pointcut Declarations
    @Pointcut("execution(* com.example.trialsdemo.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* com.example.trialsdemo.dao.*.*(..))")
    private void forDaoPackage() {
    }

    @Pointcut("execution(* com.example.trialsdemo.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    private void before(JoinPoint theJoinPoint) {

        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("======>> in @Before: calling method: " + theMethod);

        Object[] args = theJoinPoint.getArgs();
        for (Object arg : args) {
            myLogger.info("======>> argument: " + arg);
        }
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("======>> in @AfterReturning: calling method: " + theMethod);

        myLogger.info("======>> result: " + theResult);

    }
}
