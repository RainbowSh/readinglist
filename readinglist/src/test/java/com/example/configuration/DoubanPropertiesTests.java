package com.example.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Created by rainbow on 2017/1/20.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(DoubanProperties.class)
@TestPropertySource("classpath:resource.properties")
public class DoubanPropertiesTests {

    @Autowired
    private DoubanProperties doubanProperties;

    @Test
    public void testReadConfiguration() throws Exception {

        assertThat(doubanProperties.getUrl(), notNullValue());
        assertThat(doubanProperties.getUrl(), endsWith("{isbn}"));
        assertThat(doubanProperties.getUrl(), startsWith("https://"));
    }
}
