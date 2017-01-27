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
    @Path("ItemLookupResponse/Items/Item/SmallImage")
    private Image smallImage;

//    @Path("Item/MediumImage")
//    @Element
//    private Image mediumImage;
//
//    @Path("Item/LargeImage")
//    @Element
//    private Image largeImage;
//
//    @Path("Item/ItemAttributes")
//    @Element
//    private Attributes attributes;

    public Image getSmallImage() {
        return smallImage;
    }

//    public void setSmallImage(Image smallImage) {
//        this.smallImage = smallImage;
//    }

//    public Image getMediumImage() {
//        return mediumImage;
//    }
//
//    public void setMediumImage(Image mediumImage) {
//        this.mediumImage = mediumImage;
//    }
//
//    public Image getLargeImage() {
//        return largeImage;
//    }
//
//    public void setLargeImage(Image largeImage) {
//        this.largeImage = largeImage;
//    }
//
//    public Attributes getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(Attributes attributes) {
//        this.attributes = attributes;
//    }
}
