package com.example.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Rainbow on 2017/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@EnableConfigurationProperties(AmazonProperties.class)
@TestPropertySource("classpath:application.properties")
@ComponentScan("com.example.properties")
public class AmazonPropertiesTests {

    @Autowired
    private AmazonProperties amazonProperties;

    @Test
    public void name() throws Exception {
        assertThat(amazonProperties.getAssociateTag(), equalTo("BookShelf"));
        assertThat(amazonProperties.getSecretAccessKey(), notNullValue());
        assertThat(amazonProperties.getAccessKeyId(), notNullValue());
    }
}
