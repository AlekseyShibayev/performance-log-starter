package io.github.alekseyshibayev.infrastructure.log.performance.aop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class PerformanceLogServiceTest {


    @Test
    void test_1() {
        PerformanceLogService performanceLogService = new PerformanceLogService();
        performanceLogService.setExecutionTimeBorder(0);
        Assertions.assertTrue(performanceLogService.isMethodExecutionTimeLessThenExecutionTimeBorder(0L));
    }

    @Test
    void test_2() {
        PerformanceLogService performanceLogService = new PerformanceLogService();
        performanceLogService.setExecutionTimeBorder(5_000);
        Assertions.assertTrue(performanceLogService.isMethodExecutionTimeLessThenExecutionTimeBorder(100L));
    }

    @Test
    void test_3() {
        PerformanceLogService performanceLogService = new PerformanceLogService();
        performanceLogService.setExecutionTimeBorder(5_000);
        Assertions.assertFalse(performanceLogService.isMethodExecutionTimeLessThenExecutionTimeBorder(10_000L));
    }

}