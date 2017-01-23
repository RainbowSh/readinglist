package com.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Created by Rainbow on 2017/1/9.
 */
@Component
@ConfigurationProperties(prefix = "amazon")
public class AmazonProperties {
    @NotNull
    private String associateTag;
    @NotNull
    private String accessKeyId;
    @NotNull
    private String secretAccessKey;

    public String getAssociateTag() {
        return associateTag;
    }

    public void setAssociateTag(String associateTag) {
        this.associateTag = associateTag;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
}
