package com.example.domain.amazon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Rainbow on 2017/2/21.
 */
@Root(name="ItemLookupErrorResponse")
public final class AmazonErrorResponse {

    @Element(name = "Error")
    private Error error;

    public Error getError() {
        return error;
    }
}
