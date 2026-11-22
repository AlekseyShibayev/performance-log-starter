package com.company.app.infrastructure.log.performance.test.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.app.infrastructure.log.performance.aop.PerformanceLog;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityService {

    private final EntityRepository entityRepository;

    @PerformanceLog
    @Transactional
    public void experiment(Collection<String> names, ZonedDateTime createDate) {
        List<Entity> entities = entityRepository.findByNameInAndCreateDateGreaterThan(names, createDate);

        Entity first = entities.get(0);
        first.setName("new name");
        entityRepository.save(first);

        List<Long> ids = entities.stream().map(Entity::getId).limit(1).toList();
        entityRepository.deleteByIds(ids);

        entityRepository.deleteById(entities.get(2).getId());
    }

    @Transactional
    @PerformanceLog
    public List<Entity> saveAll(List<Entity> list) {
        return entityRepository.saveAll(list);
    }

    @Transactional
    @PerformanceLog
    public void errorQuery() {
        entityRepository.findAllWithDelay();
    }

}
