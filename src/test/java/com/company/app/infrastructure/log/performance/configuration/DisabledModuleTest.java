package com.company.app.infrastructure.log.performance.configuration;

import com.company.app.infrastructure.log.performance.PerformanceLogConfiguration;
import com.company.app.infrastructure.log.performance.aop.PerformanceLogAspect;
import com.company.app.infrastructure.log.performance.aop.PerformanceLogService;
import com.company.app.infrastructure.log.performance.interceptor.HibernateQueryInfoInterceptor;
import com.company.app.infrastructure.log.performance.writequeryvalues.PerformanceLogDataSourceAspect;
import com.company.app.infrastructure.log.performance.writequeryvalues.service.PerformanceLogStatisticStorage;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class DisabledModuleTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(
                    PerformanceLogConfiguration.class,
                    PerformanceLogService.class,
                    PerformanceLogAspect.class,
                    HibernateQueryInfoInterceptor.class,
                    PerformanceLogInitializer.class,
                    PerformanceLogHibernateInterceptorRegistrar.class,
                    PerformanceLogDataSourceAspect.class,
                    PerformanceLogStatisticStorage.class
            );

    @Test
    void module_is_disabled_by_default() {
        runner.run(context -> assertThat(context)
                .doesNotHaveBean(PerformanceLogConfiguration.class)
                .doesNotHaveBean(PerformanceLogAspect.class)
                .doesNotHaveBean(PerformanceLogService.class)
                .doesNotHaveBean(HibernateQueryInfoInterceptor.class)
                .doesNotHaveBean(PerformanceLogDataSourceAspect.class)
                .doesNotHaveBean(PerformanceLogStatisticStorage.class)
                .doesNotHaveBean(DefaultPointcutAdvisor.class));
    }

    @Test
    void write_query_values_does_not_enable_module() {
        runner.withPropertyValues("log.performance.write-query-values.enable=true")
                .run(context -> assertThat(context)
                        .doesNotHaveBean(PerformanceLogAspect.class)
                        .doesNotHaveBean(PerformanceLogDataSourceAspect.class)
                        .doesNotHaveBean(PerformanceLogStatisticStorage.class));
    }

    @Test
    void disabled_write_query_values_does_not_check_profiles() {
        runner.withInitializer(context -> context.getEnvironment().setActiveProfiles("prod"))
                .withPropertyValues("log.performance.enable=true")
                .run(context -> assertThat(context)
                        .hasNotFailed()
                        .doesNotHaveBean(PerformanceLogDataSourceAspect.class));
    }

    @Test
    void module_and_write_query_values_are_enabled_together() {
        runner.withInitializer(context -> context.getEnvironment().setActiveProfiles("test"))
                .withPropertyValues(
                        "log.performance.enable=true",
                        "log.performance.write-query-values.enable=true"
                )
                .run(context -> assertThat(context)
                        .hasSingleBean(PerformanceLogAspect.class)
                        .hasSingleBean(PerformanceLogDataSourceAspect.class)
                        .hasSingleBean(PerformanceLogStatisticStorage.class));
    }

    @Test
    void write_query_values_fails_fast_without_allowed_profile() {
        runner.withInitializer(context -> context.getEnvironment().setActiveProfiles("prod"))
                .withPropertyValues(
                        "log.performance.enable=true",
                        "log.performance.write-query-values.enable=true"
                )
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure())
                            .hasRootCauseInstanceOf(IllegalStateException.class)
                            .hasMessageContaining("local,test,dev");
                });
    }

}
