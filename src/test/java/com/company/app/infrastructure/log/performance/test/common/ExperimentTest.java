package com.company.app.infrastructure.log.performance.test.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.company.app.infrastructure.log.performance.test.config.BaseInfrastructureTest;
import com.company.app.infrastructure.log.performance.test.domain.Entity;
import com.company.app.infrastructure.log.performance.test.domain.EntityService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

class ExperimentTest extends BaseInfrastructureTest {

    private static final String NAME = "test_entity_name";
    private static final AtomicInteger atomic = new AtomicInteger(0);

    @Autowired
    private EntityService entityService;

    @Test
    void test_1() {
        List<Entity> entities = Stream.generate(() -> createEntity(NAME + atomic.incrementAndGet()))
                .limit(3)
                .toList();
        entities = entityService.saveAll(entities);
        List<String> names = entities.stream().map(Entity::getName).toList();

        entityService.experiment(names, ZonedDateTime.now().minusDays(2));
    }

    private Entity createEntity(String name) {
        return new Entity()
                .setName(name);
    }

}
