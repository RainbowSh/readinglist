package com.example.service;

import com.example.configuration.AmazonProperties;
import com.example.domain.amazon.AmazonBookList;
import com.example.domain.amazon.AmazonResponse;
import com.example.service.amazon.SignedRequestsHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Rainbow on 2017/1/27.
 */
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(AmazonProperties.class)
@TestPropertySource("classpath:resource.properties")
public class AmazonServiceTests {

    @Autowired
    private AmazonProperties amazonProperties;

    /*
     * Use the end-point according to the region you are interested in.
     */
    private static final String ENDPOINT = "webservices.amazon.cn";

    private String signUrl(String isbn) {

        /*
         * Set up the signed requests helper.
         */
        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, amazonProperties.getAccessKeyId(), amazonProperties
                    .getSecretAccessKey());
        } catch (Exception e) {
            e.printStackTrace();
            return StringUtils.EMPTY;
        }

        String requestUrl;

        Map<String, String> params = new HashMap<>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemLookup");
        params.put("AWSAccessKeyId", amazonProperties.getAccessKeyId());
        params.put("AssociateTag", amazonProperties.getAssociateTag());
        params.put("ItemId", isbn);
        params.put("IdType", "ISBN");
        params.put("ResponseGroup", "Images,ItemAttributes");
        params.put("SearchIndex", "Books");

        requestUrl = helper.sign(params);

        return requestUrl;
    }

    @Test
    public void testQueryBookFromAmazonUseRestTemplate() throws Exception {
        String serviceUrl = signUrl("9787302423287");

        RestTemplate template = new RestTemplate();

        String response = template.getForObject(URI.create(serviceUrl), String.class);
        Serializer serializer = new Persister();
        AmazonBookList bookList = serializer.read(AmazonBookList.class, response, false);

        assertThat(bookList.getBooks(), notNullValue());
        assertThat(bookList.getBooks().size(), equalTo(1));
        assertThat(bookList.getBooks().get(0), notNullValue());
//        assertThat(bookList.getData().get(1).getAttributes().getTitle(), equalTo("机器学习"));
    }
}
