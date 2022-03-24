package com.twitter.bugerbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class JacksonConfiguration {
    private final ObjectMapper objectMapper;
    private final static Logger logger = LoggerFactory.getLogger(JacksonConfiguration.class);

    public JacksonConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    ObjectMapper jacksonObjectMapper() {
        logger.info("Creating JacksonConfiguration for object Mapper");
        objectMapper.registerModule(new JsonNullableModule());
        return objectMapper;
    }
}
