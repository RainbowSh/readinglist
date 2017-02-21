package com.example.domain.amazon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by rainbow on 2017/2/5.
 */
@Root
public class Error {

    @Element(name = "Code")
    private String code;

    @Element(name = "Message")
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isInvalidIsbn() {
        return code.equalsIgnoreCase("AWS.InvalidParameterValue");
    }

    @Override
    public String toString() {
        return "Error{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
