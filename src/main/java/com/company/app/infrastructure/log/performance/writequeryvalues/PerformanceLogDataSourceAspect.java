package com.company.app.infrastructure.log.performance.writequeryvalues;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import com.company.app.infrastructure.log.performance.writequeryvalues.delegate.ConnectionDelegate;
import com.company.app.infrastructure.log.performance.writequeryvalues.service.PerformanceLogStatisticStorage;

import javax.annotation.PostConstruct;
import java.sql.Connection;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${log.performance.enable:false} && ${log.performance.write-query-values.enable:false}")
public class PerformanceLogDataSourceAspect {

    private final PerformanceLogStatisticStorage performanceLogStatisticStorage;

    @PostConstruct
    void init() {
        log.debug("**********     created [{}]     **********", this.getClass().getName());
    }

    @Pointcut("execution(* javax.sql.DataSource.*(..))")
    public void twoDataSourceMethodsWhichReturnConnection() {
    }

    @SneakyThrows
    @Around("twoDataSourceMethodsWhichReturnConnection()")
    public Object wrapByDecorator(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = callOriginalMethod(proceedingJoinPoint);
        if (result instanceof Connection connection) {
            return ConnectionDelegate.of(connection, performanceLogStatisticStorage);
        } else {
            return result;
        }
    }

    private Object callOriginalMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

}
