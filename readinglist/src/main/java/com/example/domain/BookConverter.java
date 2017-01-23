package com.example.domain;

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

    public static Book from(Document doc) {

        checkError(doc);

        Book book = new Book();


        return book;
    }

    private static void checkError(Document doc) {
        Node errorNode = doc.selectSingleNode("//Error");
        if (errorNode != null) {

        }
    }
}
