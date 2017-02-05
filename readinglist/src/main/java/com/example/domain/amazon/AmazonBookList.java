package com.example.domain.amazon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Rainbow on 2017/1/27.
 */
@Root
public class AmazonBookList {

    @Element(name = "Request", required = false)
    private Request request;

    @ElementList(entry = "Item", inline = true, required = false)
    private List<AmazonBook> items;

    public Request getRequest() {
        return request;
    }

    public List<AmazonBook> getBooks() {
        return items;
    }

}
