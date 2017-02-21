package com.example.service;

import org.springframework.http.HttpStatus;

/**
 * Created by Rainbow on 2017/2/21.
 */
public final class RestUtils {

    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

    public static boolean isOk(HttpStatus status) {
        return status == HttpStatus.OK;
    }
}
