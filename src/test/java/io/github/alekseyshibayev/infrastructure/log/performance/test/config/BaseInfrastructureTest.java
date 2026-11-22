package io.github.alekseyshibayev.infrastructure.log.performance.test.config;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(classes = {DbTestConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
//@ContextConfiguration(initializers = TaskApplicationContextInitializer.class)
@Testcontainers(disabledWithoutDocker = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseInfrastructureTest {

    @Autowired
    protected TransactionTemplate transactionTemplate;

}
