package com.example.domain.amazon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by Rainbow on 2017/1/27.
 */
@Root
public class AmazonBook {

    @Element(name = "SmallImage")
    private Image smallImage;

    @Element(name = "MediumImage")
    private Image mediumImage;

    @Element(name = "LargeImage")
    private Image largeImage;

    @Element(name = "ItemAttributes")
    private Attributes attributes;

    public Image getSmallImage() {
        return smallImage;
    }

    public Image getMediumImage() {
        return mediumImage;
    }

    public Image getLargeImage() {
        return largeImage;
    }

    public Attributes getAttributes() {
        return attributes;
    }

}
