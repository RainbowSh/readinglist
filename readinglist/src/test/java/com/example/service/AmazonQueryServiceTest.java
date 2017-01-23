package com.example.service;

import com.example.configuration.AmazonProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Rainbow on 2017/1/16.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(AmazonProperties.class)
@ComponentScan("com.example.properties")
@TestPropertySource("classpath:application.properties")
public class AmazonQueryServiceTest {

    @Autowired
    private AmazonProperties amazonProperties;
    private AmazonQueryService amazonQueryService;

    @Before
    public void setUp() {
        amazonQueryService = new AmazonQueryService(amazonProperties);
    }

    @Test
    public void testQueryByISBN() throws Exception {

        Assert.assertNull(amazonQueryService.queryByISBN("9787302423288"));
    }
}
