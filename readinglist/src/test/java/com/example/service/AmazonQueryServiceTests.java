package com.example.service;

import com.example.configuration.AmazonProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Rainbow on 2017/1/16.
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = AmazonQueryServiceConfiguration.class)
public class AmazonQueryServiceTests {

    @Autowired
    private AmazonQueryService amazonQueryService;

    @Test
    public void testQueryByISBN() throws Exception {

        Assert.assertNull(amazonQueryService.queryByISBN("9787302423288"));
    }
}
