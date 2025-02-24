package com.nocountry.quo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class ApiConfig {
    // Define un Bean de RestTemplate para poder inyectarlo en otras clases
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
