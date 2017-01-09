package com.example.service;

import com.example.configuration.AmazonProperties;
import com.example.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Rainbow on 2017/1/9.
 */
public class AmazonQueryService implements IBookQueryService {
    private AmazonProperties amazonProperties;

    @Autowired
    public AmazonQueryService(AmazonProperties amazonProperties){
        this.amazonProperties = amazonProperties;
    }

    @Override
    public Book queryByISBN(String isbn) {
        return null;
    }
}
