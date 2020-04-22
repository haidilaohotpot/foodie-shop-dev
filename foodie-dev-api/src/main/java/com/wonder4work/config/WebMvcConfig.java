package com.wonder4work.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @since 1.0.0 2020/4/18
 */
@Configuration
public class WebMvcConfig {


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {


        return builder.build();
    }

}
