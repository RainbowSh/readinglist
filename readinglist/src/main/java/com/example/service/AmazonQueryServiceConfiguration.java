package com.example.service;

import com.example.configuration.AmazonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Rainbow on 2017/1/27.
 */
@Configuration
@EnableConfigurationProperties(AmazonProperties.class)
public class AmazonQueryServiceConfiguration {
    @Autowired
    public AmazonProperties amazonProperties;

    @Bean
    public AmazonQueryService amazonQueryService(){
        return new AmazonQueryService();
    }
}
