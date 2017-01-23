package com.example.service;

import com.example.configuration.DoubanProperties;
import com.example.domain.Book;
import com.example.domain.BookConverter;
import com.example.domain.douban.DoubanBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rainbow on 2017/1/9.
 */
@Service
public class DoubanQueryService implements BookQueryService {

    @Autowired
    private DoubanProperties doubanProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Book queryByISBN(String isbn) {

        DoubanBook book = query(isbn);

        return BookConverter.from(book);
    }

    private DoubanBook query(String isbn) {

        Map<String, String> params = new HashMap<>();
        params.put("isbn", isbn);

        return restTemplate.getForObject(doubanProperties.getUrl(), DoubanBook.class, params);
    }
}
