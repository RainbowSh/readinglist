package com.example.domain.amazon;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Rainbow on 2017/1/27.
 */
@Root(name = "ItemLookupResponse")
public class AmazonBookList {

    @ElementList(entry = "Item", inline = true)
    private List<AmazonBook> items;

    public List<AmazonBook> getBooks() {
        return items;
    }

//    public void setData(List<AmazonBook> books) {
//        this.books = books;
//    }
}
