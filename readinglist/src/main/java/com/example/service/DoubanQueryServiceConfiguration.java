package com.example.service;

import com.example.configuration.DoubanProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Rainbow on 2017/1/23.
 */
@Configuration
@EnableConfigurationProperties(DoubanProperties.class)
public class DoubanQueryServiceConfiguration {

    @Autowired
    public DoubanProperties doubanProperties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DoubanQueryService doubanQueryService() {
        return new DoubanQueryService();
    }
}
