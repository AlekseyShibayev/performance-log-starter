package com.company.app.infrastructure.log.performance.writequeryvalues.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import com.company.app.infrastructure.log.performance.interceptor.HibernateQueryInfoInterceptor;
import com.company.app.infrastructure.log.performance.writequeryvalues.common.ThrowingSQLSupplier;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// TODO: move thread local variable from HibernateQueryInfoInterceptor to this class
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnExpression("${log.performance.enable:false} && ${log.performance.write-query-values.enable:false}")
public class PerformanceLogStatisticStorage {

    private static final String TEMPLATE = "[%s] execution time [%s] ms, real sql:\n%s";
    private static final String ERROR_TEMPLATE = "[%s] interrupt at [%s] ms, real sql:\n%s\nreason:[%s]";

    private final HibernateQueryInfoInterceptor hibernateQueryInfoInterceptor;

    /**
     * Save sql query with values in thread local map.
     */
    public void addQueryWithValues(String query) {
        hibernateQueryInfoInterceptor.addQueryWithValues(query);
    }

    public <T> T execute(PreparedStatement preparedStatement, ThrowingSQLSupplier<T> supplier) throws SQLException {
        LocalDateTime now = LocalDateTime.now();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            T t = supplier.executeQuery();

            stopWatch.stop();
            long methodExecutionTime = stopWatch.getTotalTimeMillis();
            String realSql = preparedStatement.toString();

            String logMessage = TEMPLATE.formatted(now.truncatedTo(ChronoUnit.MILLIS), methodExecutionTime, realSql);
            addQueryWithValues(logMessage);

            return t;
        } catch (Exception e) {
            stopWatch.stop();
            long methodExecutionTime = stopWatch.getTotalTimeMillis();
            String realSql = preparedStatement.toString();

            String logMessage = ERROR_TEMPLATE.formatted(now.truncatedTo(ChronoUnit.MILLIS), methodExecutionTime, realSql, e.getMessage());
            addQueryWithValues(logMessage);

            throw e;
        }
    }

}
