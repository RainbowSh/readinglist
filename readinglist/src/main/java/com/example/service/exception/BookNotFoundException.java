package com.example.service.exception;

/**
 * Created by rainbow on 2017/2/6.
 */
public final class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}
