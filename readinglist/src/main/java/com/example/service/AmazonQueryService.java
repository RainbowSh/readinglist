package com.example.service;

import com.example.configuration.AmazonProperties;
import com.example.domain.Book;
import com.example.service.amazon.AmazonClient;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.service.amazon.AmazonClient.documentToString;

/**
 * Created by Rainbow on 2017/1/9.
 */
public class AmazonQueryService implements BookQueryService {

    private final static String QUERY_STR = "Service=AWSECommerceService&" +
            "Operation=ItemLookup&" +
            "ResponseGroup=Images,ItemAttributes&" +
            "SearchIndex=Books&" +
            "IdType=ISBN&" +
            "ItemId=";

    private AmazonProperties amazonProperties;

    @Autowired
    public AmazonQueryService(AmazonProperties amazonProperties){
        this.amazonProperties = amazonProperties;
    }

    @Override
    public Book queryByISBN(String isbn) {

        AmazonClient client = new AmazonClient(this.amazonProperties.getAccessKeyId(), this.amazonProperties
                .getSecretAccessKey(), this.amazonProperties.getAssociateTag());

        final String queryString = QUERY_STR + isbn;

        Document doc = client.getXml(queryString);

        System.out.println(documentToString(doc));
        return null;
    }
}
