package com.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Rainbow on 2017/1/9.
 */
@Component
@ConfigurationProperties("douban")
public class DoubanProperties {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
