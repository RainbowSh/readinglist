package com.example.service.exception;

/**
 * Created by rainbow on 2017/2/6.
 */
public final class AmazonServiceException extends RuntimeException {

    public AmazonServiceException(String message) {
        super(message);
    }

    public AmazonServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
