package com.example.controller;

import com.example.model.Book;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rainbow on 2017/1/7.
 */
@RestController
@RequestMapping("/query")
public class BookShelfController {

    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
    public Book queryByISBN(@PathVariable("isbn") String isbn) {
        Book book = new Book();

        if (!ISBNValidator.getInstance().isValid(isbn)){
//            throw new InvalidFormatException();
        }

        book.setAuthor("aoyi");
        book.setReader("aoyi");
        book.setTitle("Old man and sea");
        book.setIsbn(isbn);

        return book;
    }

}
