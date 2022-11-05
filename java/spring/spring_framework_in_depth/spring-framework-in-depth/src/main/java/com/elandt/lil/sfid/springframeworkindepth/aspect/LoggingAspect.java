package com.elandt.lil.sfid.springframeworkindepth.aspect;

import java.util.Arrays;
import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)")
    public void executeLogging(){}


    /*
     * Having all three of these examples is nonsensical in practice as it leads to triplicate logging.
     */

    @Before("executeLogging()")
    public void logMethodCall(JoinPoint joinPoint) {
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        // Grabbing args from join point - be careful doing this in production...ARGS MAY BE SENSITIVE
        Object[] args = joinPoint.getArgs();
        if (null != args && args.length > 0) {
            message.append(" args[ | ");
            Arrays.asList(args).forEach(arg -> {
                message.append(arg).append(" | ");
            });
            message.append("]");
        }
        LOGGER.info(message.toString());
    }

    // This does not execute if the annotated method throws an exception because we expect it to return
    @AfterReturning(value = "executeLogging()", returning = "returnValue")
    public void logMethodCall(JoinPoint joinPoint, Object returnValue) {
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        // Grabbing args from join point - be careful doing this in production...ARGS MAY BE SENSITIVE
        Object[] args = joinPoint.getArgs();
        if (null != args && args.length > 0) {
            message.append(" args[ | ");
            Arrays.asList(args).forEach(arg -> {
                message.append(arg).append(" | ");
            });
            message.append("]");
        }
        if (returnValue instanceof Collection) {
            message.append(", returning: ").append(((Collection)returnValue).size()).append(" instance(s)");
        } else {
            message.append(", returning: ").append(returnValue.toString());
        }
        LOGGER.info(message.toString());
    }

    /*
     * Will execute regardless of an exception being thrown by the annotated method.
     * This is likely the most useful of the 3.
     */
    @Around(value = "executeLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // Everything before .proceed() is executed before the annotated method is executed
        Object returnValue = joinPoint.proceed();
        // Everything after .proceed() occurs after the  method is excuted
        long totalTime = System.currentTimeMillis() - startTime;
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        message.append(" totalTime: ").append(totalTime).append("ms");
        // Grabbing args from join point - be careful doing this in production...ARGS MAY BE SENSITIVE
        Object[] args = joinPoint.getArgs();
        if (null != args && args.length > 0) {
            message.append(" args[ | ");
            Arrays.asList(args).forEach(arg -> {
                message.append(arg).append(" | ");
            });
            message.append("]");
        }
        if (returnValue instanceof Collection) {
            message.append(", returning: ").append(((Collection)returnValue).size()).append(" instance(s)");
        } else {
            message.append(", returning: ").append(returnValue.toString());
        }
        LOGGER.info(message.toString());
        return returnValue;
    }
}
