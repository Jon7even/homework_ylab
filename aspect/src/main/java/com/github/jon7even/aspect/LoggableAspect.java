package com.github.jon7even.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Обработка поведения логирования методов, которые помечены аннотацией @Loggable
 *
 * @author Jon7even
 * @version 1.0
 */
@Aspect
public class LoggableAspect {
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable() {
    }

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        System.out.println("Execution of method " + proceedingJoinPoint.getSignature()
                + "finished. Execution time is " + end + " ms.");
        return result;
    }
}
