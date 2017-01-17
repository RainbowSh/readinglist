package com.example.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by rainbow on 2017/1/17.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(AmazonProperties.class)
@TestPropertySource("classpath:application.properties")
public class AmazonPropertiesTests {
    @Autowired
    private AmazonProperties amazonProperties;

    @Test
    public void readProperties() throws Exception {
        assertThat(amazonProperties.getAssociateTag(), equalTo("BookShelf"));
    }
}
