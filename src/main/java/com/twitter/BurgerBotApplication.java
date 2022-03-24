package com.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BurgerBotApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(BurgerBotApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        logger.info("Starting SpringApplicationBuilder BurgerBotApplication API");
        return application.sources(BurgerBotApplication.class);

    }

    public static void main(String... args) {
        logger.info("Starting BurgerBotApplication API");
        SpringApplication.run(BurgerBotApplication.class, args);

    }
}
