package com.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Created by Rainbow on 2017/1/9.
 */
@Component
@ConfigurationProperties(locations = "classpath:resource.properties", prefix = "douban")
public class DoubanProperties {
    @NotNull
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
