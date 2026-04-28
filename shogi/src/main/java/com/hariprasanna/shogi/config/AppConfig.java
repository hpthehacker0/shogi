package com.hariprasanna.shogi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // This explicitly creates the ObjectMapper and puts it in the Spring Boot container
    // so your GameService can find it!
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}