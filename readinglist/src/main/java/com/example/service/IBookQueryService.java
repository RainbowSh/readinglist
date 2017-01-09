package com.example.service;

import com.example.domain.Book;

/**
 * Created by Rainbow on 2017/1/9.
 */
public interface IBookQueryService {
    Book queryByISBN(String isbn);
}
