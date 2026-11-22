package io.github.alekseyshibayev.infrastructure.log.performance.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import io.github.alekseyshibayev.infrastructure.log.performance.aop.PerformanceLogAspect;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "log.performance", name = "enable", havingValue = "true")
public class PerformanceLogInitializer {

    @EventListener({ContextRefreshedEvent.class})
    void init() {
        LoggingSystem system = LoggingSystem.get(PerformanceLogAspect.class.getClassLoader());
        system.setLogLevel("io.github.alekseyshibayev.infrastructure.log.performance", LogLevel.DEBUG);
        log.info("performance log debug enable");
    }

}
