package com.company.app.infrastructure.log.performance.test.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import com.company.app.infrastructure.log.performance.test.config.BaseInfrastructureTest;
import com.company.app.infrastructure.log.performance.test.domain.EntityService;

class ErrorTest extends BaseInfrastructureTest {

    @Autowired
    private EntityService entityService;

    @Test
    void test_1() {
        // exception type depends on the driver; the point is that the proxy does not swallow it
        Assertions.assertThatCode(() -> entityService.errorQuery())
                .isInstanceOf(DataAccessException.class);
    }

}
