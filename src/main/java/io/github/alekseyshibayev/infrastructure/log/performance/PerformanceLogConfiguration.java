package io.github.alekseyshibayev.infrastructure.log.performance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.alekseyshibayev.infrastructure.log.performance.aop.PerformanceLogAspect;
import io.github.alekseyshibayev.infrastructure.log.performance.aop.PerformanceLogService;
import io.github.alekseyshibayev.infrastructure.log.performance.interceptor.HibernateQueryInfoInterceptor;

/**
 * PerformanceLog module
 * <p>
 * Wraps the method by proxy, which writes the method execution time to log.
 * Additional do size log, if return result type is Collection, or Slice (result of Pageable query).
 * Additional do hibernate query log.
 * <p>
 * For use need:
 * 1. log.performance.enable: true
 * 2. log.performance.pointcut-expression: aspectJ pointcut expression
 * for example:
 * log.performance.pointcut-expression:
 * @annotation(org.springframework.scheduling.annotation.Scheduled)
 * || @within(org.springframework.web.bind.annotation.RestController)
 * default - class or method marked by PerformanceLog annotation
 * <p>
 * Additional parameters:
 * 1. For limit log by method execution time use:
 * log.performance.do-not-write-to-log-methods-whose-execution-time-is-less-than-X-ms
 * default - 0 ms
 * 2. For show real sql query with values:
 * log.performance.write-query-values.enable=true
 * <p>
 * Real sql values may contain PII, so they are logged only when all active Spring profiles are one of
 * local, test, dev (hardcoded in WriteQueryValuesProfileGuard).
 * Otherwise the application fails fast during startup.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "log.performance", name = "enable", havingValue = "true")
public class PerformanceLogConfiguration {

    @Value("${log.performance.pointcut-expression:"
            + "@annotation(io.github.alekseyshibayev.infrastructure.log.performance.aop.PerformanceLog)"
            + " || @within(io.github.alekseyshibayev.infrastructure.log.performance.aop.PerformanceLog)}")
    private String expression;

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(HibernateQueryInfoInterceptor hibernateQueryInfoInterceptor,
                                                         PerformanceLogService performanceLogService) {
        var aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);

        var performanceLogAspect = new PerformanceLogAspect(hibernateQueryInfoInterceptor, performanceLogService);

        var defaultPointcutAdvisor = new DefaultPointcutAdvisor(aspectJExpressionPointcut, performanceLogAspect);
        log.info("**********     created [{}] with pointcut expression [{}]     **********",
                DefaultPointcutAdvisor.class.getName(), expression);

        return defaultPointcutAdvisor;
    }

}