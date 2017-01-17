package com.example.service;

import com.example.configuration.DoubanProperties;
import com.example.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Rainbow on 2017/1/9.
 */
public class DoubanQueryService implements BookQueryService {
    private DoubanProperties doubanProperties;

    @Autowired
    public DoubanQueryService(DoubanProperties doubanProperties) {
        this.doubanProperties = doubanProperties;
    }

    @Override
    public Book queryByISBN(String isbn) {
        return null;
    }
}
