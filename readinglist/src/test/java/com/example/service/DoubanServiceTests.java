package com.example.service;

import com.example.configuration.DoubanProperties;
import com.example.domain.douban.DoubanBook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by rainbow on 2017/1/19.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(DoubanProperties.class)
@TestPropertySource("classpath:resource.properties")
public class DoubanServiceTests {
    @Autowired
    private DoubanProperties doubanProperties;

    @Test
    public void testQueryBookInfo() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("isbn", "9787505715660");

        ResponseEntity<String> response = restTemplate.getForEntity(doubanProperties.getUrl(), String.class, params);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void testQueryByISBN() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("isbn", "9787505715660");

        DoubanBook doubanBook = restTemplate.getForObject(doubanProperties.getUrl(), DoubanBook.class, params);

        assertThat(doubanBook, notNullValue());
        assertThat(doubanBook.getAuthor(), notNullValue());
        assertThat(doubanBook.getAuthor().size(), equalTo(1));
        assertThat(doubanBook.getAuthor().get(0), equalTo("（法）圣埃克苏佩里"));
        assertThat(doubanBook.getTitle(), equalTo("小王子"));
    }
}
