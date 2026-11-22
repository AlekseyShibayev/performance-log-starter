package io.github.alekseyshibayev.infrastructure.log.performance.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;
import io.github.alekseyshibayev.infrastructure.log.performance.interceptor.HibernateQueryInfoInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "log.performance", name = "enable", havingValue = "true")
public class PerformanceLogHibernateInterceptorRegistrar implements HibernatePropertiesCustomizer {

    private final HibernateQueryInfoInterceptor hibernateQueryInfoInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", hibernateQueryInfoInterceptor);
        hibernateProperties.put("hibernate.session_factory.statement_inspector", hibernateQueryInfoInterceptor);
    }

}