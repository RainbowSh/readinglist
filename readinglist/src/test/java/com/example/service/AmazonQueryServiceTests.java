package com.example.service;

import com.example.configuration.AmazonProperties;
import com.example.domain.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

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

        Book expect = amazonQueryService.queryByISBN("9787302423288");

        assertThat(expect.getIsbn(), equalTo("9787302423288"));
    }
}
