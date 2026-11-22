package com.company.app.infrastructure.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import com.company.app.StarterConfiguration;

@EnableCaching
@SpringBootApplication(exclude = {StarterConfiguration.class})
public class SpringBootApplicationConfig {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationConfig.class, args);
    }

}