package com.jesusfernandez.superheroapi.annotation.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimedAspect {

    @Around("@annotation(com.jesusfernandez.superheroapi.annotation.interfaces.Timed)")
    public Object timed(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info(joinPoint.getSignature().getName() + " executed in " + executionTime + "ms");

        return proceed;

    }

}
