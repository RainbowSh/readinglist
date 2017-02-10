package com.example.domain;

import com.example.domain.amazon.AmazonBook;
import com.example.domain.douban.DoubanBook;
import org.dom4j.Document;
import org.dom4j.Node;

/**
 * Created by rainbow on 2017/1/21.
 */
public final class BookConverter {

    public static Book from(DoubanBook source) {

        Book book = new Book();

        book.setIsbn(source.getIsbn13());
        book.setTitle(source.getTitle());
        book.setAuthor(String.join(",", source.getAuthor()));
        book.setDescription(source.getSummary());
        book.setPublisher(source.getPublisher());
        book.setImageUrl(source.getImage());

        return book;
    }

    public static Book from(AmazonBook source) {

        Book book = new Book();

        book.setIsbn(source.getAttributes().getIsbn());
        book.setTitle(source.getAttributes().getTitle());
        book.setAuthor(source.getAttributes().getAuthor());
        book.setPublisher(source.getAttributes().getPublisher());
        book.setImageUrl(source.getSmallImage().getUrl());
        book.setDescription(source.getAttributes().getBinding());

        return book;
    }
}
