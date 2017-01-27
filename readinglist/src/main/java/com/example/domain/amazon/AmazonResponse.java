package com.example.domain.amazon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Rainbow on 2017/1/27.
 */
@Root
public class AmazonResponse {

    @Element(name = "Items")
    private AmazonBookList data;

    public AmazonBookList getData() {
        return data;
    }

    public void setData(AmazonBookList data) {
        this.data = data;
    }
}
