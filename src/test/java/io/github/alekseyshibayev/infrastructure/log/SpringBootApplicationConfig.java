package io.github.alekseyshibayev.infrastructure.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import io.github.alekseyshibayev.StarterConfiguration;

@EnableCaching
@SpringBootApplication(exclude = {StarterConfiguration.class})
public class SpringBootApplicationConfig {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationConfig.class, args);
    }

}