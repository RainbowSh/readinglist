package com.example.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by rainbow on 2017/1/17.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(AmazonProperties.class)
@TestPropertySource("classpath:resource.properties")
public class AmazonPropertiesTests {
    @Autowired
    private AmazonProperties amazonProperties;

    @Test
    public void readProperties() throws Exception {
        assertThat(amazonProperties.getAssociateTag(), equalTo("BookShelf"));

        assertThat(amazonProperties.getAccessKeyId(), notNullValue());
        assertThat(amazonProperties.getAccessKeyId().length(), equalTo(20));
        assertThat(amazonProperties.getAccessKeyId(), startsWith("AK"));
        assertThat(amazonProperties.getAccessKeyId(), endsWith("GA"));

        assertThat(amazonProperties.getSecretAccessKey(), notNullValue());
        assertThat(amazonProperties.getSecretAccessKey().length(), equalTo(40));
        assertThat(amazonProperties.getSecretAccessKey(), startsWith("DE"));
        assertThat(amazonProperties.getSecretAccessKey(), endsWith("MG"));
    }
}
