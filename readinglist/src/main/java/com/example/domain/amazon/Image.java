package com.example.domain.amazon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Rainbow on 2017/1/27.
 */
public class Image {
    @Element(name = "URL")
    private String url;

    @Element(name = "Height")
    private Size height;

    @Element(name = "Width")
    private Size width;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Size getHeight() {
        return height;
    }

    public void setHeight(Size height) {
        this.height = height;
    }

    public Size getWidth() {
        return width;
    }

    public void setWidth(Size width) {
        this.width = width;
    }
}
