package com.rush.banking.authorityservice.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class LogProvider {

    @Around("execution((public * com.rush.banking.authorityservice.controller.*.*(..))")
    public void logBeforeAllControllerMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info("Inside "+ proceedingJoinPoint.getSignature().getName()+ " of User Controller");
        log.info("Method "+ proceedingJoinPoint.getSignature().getName()+ " returned: {}", (List<String>) proceedingJoinPoint.proceed());
    }
}
