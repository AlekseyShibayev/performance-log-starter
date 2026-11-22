package io.github.alekseyshibayev.infrastructure.log.performance.test.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import io.github.alekseyshibayev.infrastructure.log.performance.aop.PerformanceLog;

import javax.persistence.QueryHint;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

public interface EntityRepository extends JpaRepository<Entity, Long> {

    @PerformanceLog
    List<Entity> findByNameInAndCreateDateGreaterThan(Collection<String> names, ZonedDateTime createDate);

    @Query(value = "SELECT pg_sleep(5); SELECT * FROM entity", nativeQuery = true)
    @QueryHints(@QueryHint(name = "javax.persistence.query.timeout", value = "1000"))
    List<Entity> findAllWithDelay();

    @PerformanceLog
    @Modifying
    @Query("delete from Entity e where e.id in :ids")
    void deleteByIds(Collection<Long> ids);

}
