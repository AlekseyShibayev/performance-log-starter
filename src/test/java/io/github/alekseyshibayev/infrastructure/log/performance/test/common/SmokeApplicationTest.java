package io.github.alekseyshibayev.infrastructure.log.performance.test.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import io.github.alekseyshibayev.infrastructure.log.performance.test.config.BaseInfrastructureTest;


class SmokeApplicationTest extends BaseInfrastructureTest {

    @Autowired
    private ApplicationContext applicationContext;

//    @Test
    void spring_application_context_can_rise() {
        Assertions.assertNotNull(applicationContext);
    }

}