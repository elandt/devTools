package com.elandt.lil.sfid.springframeworkindepth.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CountingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountingAspect.class);

    private static final Map<String, Integer> methodCallCounts = new HashMap<>();

    @Pointcut("@annotation(Countable)")
    public void executeCounting(){}

    @Before("executeCounting()")
    public void countMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        if (methodCallCounts.containsKey(methodName)) {
            methodCallCounts.put(methodName, methodCallCounts.get(methodName) + 1);
        } else {
            methodCallCounts.put(methodName, 1);
        }
        
        StringBuilder message = new StringBuilder("Method: ");
        message.append(methodName)
            .append(", called: ")
            .append(methodCallCounts.get(methodName).intValue())
            .append(" times.");

        LOGGER.info(message.toString());        
    }
    
}
