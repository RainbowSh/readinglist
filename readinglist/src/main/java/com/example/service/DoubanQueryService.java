package com.example.service;

import com.example.configuration.DoubanProperties;
import com.example.domain.Book;
import com.example.domain.BookConverter;
import com.example.domain.douban.DoubanBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rainbow on 2017/1/9.
 */
public class DoubanQueryService implements BookQueryService {

    private final DoubanProperties doubanProperties;

    @Autowired
    public DoubanQueryService(DoubanProperties doubanProperties) {

        this.doubanProperties = doubanProperties;
    }

    @Override
    public Book queryByISBN(String isbn) {

        DoubanBook book = query(isbn);

        return BookConverter.from(book);
    }

    private DoubanBook query(String isbn) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("isbn", isbn);

        return restTemplate.getForObject(doubanProperties.getUrl(), DoubanBook.class, params);
    }
}
