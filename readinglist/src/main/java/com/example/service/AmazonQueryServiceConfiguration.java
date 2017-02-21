package com.example.service;

import com.example.configuration.AmazonProperties;
import com.example.service.amazon.AmazonResponseErrorHandler;
import com.example.service.amazon.SignedRequestsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Rainbow on 2017/1/27.
 */
@Configuration
@EnableConfigurationProperties(AmazonProperties.class)
public class AmazonQueryServiceConfiguration {
    /*
     * Use the end-point according to the region you are interested in.
     */
    private static final String ENDPOINT = "webservices.amazon.cn";

    @Autowired
    public AmazonProperties amazonProperties;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.setErrorHandler(amazonResponseErrorHandler());

        return template;
    }

    @Bean()
    public SignedRequestsHelper signedRequestsHelper() {
        try {
            return SignedRequestsHelper.getInstance(ENDPOINT, amazonProperties.getAccessKeyId(), amazonProperties
                    .getSecretAccessKey());
        } catch (Exception e) {
            throw new RuntimeException("Initial SignedRequestHelper failed.", e.getCause());
        }
    }

    @Bean
    public AmazonResponseErrorHandler amazonResponseErrorHandler() {
        return new AmazonResponseErrorHandler();
    }

    @Bean
    public AmazonQueryService amazonQueryService(){
        return new AmazonQueryService();
    }
}
