package io.github.alekseyshibayev.infrastructure.log.performance.aop;


import java.util.Collection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import io.github.alekseyshibayev.infrastructure.log.performance.PerformanceLogConfiguration;
import io.github.alekseyshibayev.infrastructure.log.performance.interceptor.HibernateQueryInfoInterceptor;
import io.github.alekseyshibayev.infrastructure.log.performance.interceptor.model.QueryInfo;

/**
 * {@link PerformanceLogConfiguration}
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "log.performance", name = "enable", havingValue = "true")
public class PerformanceLogAspect implements MethodInterceptor {

    private final HibernateQueryInfoInterceptor hibernateQueryInfoInterceptor;
    private final PerformanceLogService performanceLogService;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (log.isDebugEnabled()) {
            String key = hibernateQueryInfoInterceptor.createKey(invocation);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            return doOriginalMethodWithAdditionalLogging(invocation, key, stopWatch);
        } else {
            return invocation.proceed();
        }
    }

    private Object doOriginalMethodWithAdditionalLogging(MethodInvocation invocation, String key, StopWatch stopWatch) throws Throwable {
        try {
            Object result = invocation.proceed();

            stopWatch.stop();
            QueryInfo queryInfo = hibernateQueryInfoInterceptor.getQueryInfo(key);

            writeToLog(stopWatch, key, result, queryInfo);
            return result;
        } catch (Exception e) {
            writeToLog(key, stopWatch);
            throw e;
        } finally {
            hibernateQueryInfoInterceptor.remove(key);
        }
    }

    private void writeToLog(String key, StopWatch stopWatch) {
        stopWatch.stop();
        QueryInfo queryInfo = hibernateQueryInfoInterceptor.getQueryInfo(key);
        long methodExecutionTime = stopWatch.getTotalTimeMillis();

        if (performanceLogService.isMethodExecutionTimeLessThenExecutionTimeBorder(methodExecutionTime)) {
            return;
        }

        log.debug("method execution time: [{}] ms, source: [{}], query info: \n{}",
                methodExecutionTime,
                key,
                queryInfo
        );
    }

    private void writeToLog(StopWatch stopWatch, String classAndMethodName, Object result, QueryInfo queryInfo) {
        long methodExecutionTime = stopWatch.getTotalTimeMillis();

        if (performanceLogService.isMethodExecutionTimeLessThenExecutionTimeBorder(methodExecutionTime)) {
            return;
        }

        if (result instanceof Collection<?> collection) {
            log.debug("method execution time: [{}] ms, return collection, size: [{}], source: [{}], query info: \n{}",
                    methodExecutionTime,
                    collection.size(),
                    classAndMethodName,
                    queryInfo
            );
        } else if (result instanceof Slice<?> slice) {
            log.debug("method execution time: [{}] ms, return slice, size: [{}], source: [{}], query info: \n{}",
                    methodExecutionTime,
                    slice.getContent().size(),
                    classAndMethodName,
                    queryInfo
            );
        } else {
            log.debug("method execution time: [{}] ms, source: [{}], query info: \n{}",
                    methodExecutionTime,
                    classAndMethodName,
                    queryInfo
            );
        }
    }

}