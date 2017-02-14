package com.example.service;

import com.example.configuration.AmazonProperties;
import com.example.domain.Book;
import com.example.domain.BookConverter;
import com.example.domain.amazon.AmazonBook;
import com.example.domain.amazon.AmazonResponse;
import com.example.domain.amazon.Error;
import com.example.service.amazon.SignedRequestsHelper;
import com.example.service.exception.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rainbow on 2017/1/9.
 */
@Service
public class AmazonQueryService implements BookQueryService {
//    /*
//     * Use the end-point according to the region you are interested in.
//     */
//    private static final String ENDPOINT = "webservices.amazon.cn";

    @Autowired
    private AmazonProperties amazonProperties;

    @Autowired
    private RestTemplate template;

    @Autowired
    private SignedRequestsHelper helper;
//    {
//        try {
//            helper = SignedRequestsHelper.getInstance(ENDPOINT, amazonProperties.getAccessKeyId(), amazonProperties
//                    .getSecretAccessKey());
//        } catch (Exception e) {
//            throw new RuntimeException("Initial SignedRequestHelper failed.", e.getCause());
//        }
//    }

    @Override
    public Book queryByISBN(String isbn) {
        AmazonBook book = query(isbn);

        return BookConverter.from(book);
    }

    private AmazonBook query(String isbn) {

        String serviceUrl = signUrl(isbn);

        ResponseEntity<String> response = template.getForEntity(URI.create(serviceUrl), String.class);

        switch (response.getStatusCode()) {
            case OK:
                return parseResponse(response.getBody());
            case FORBIDDEN:

            case UNAUTHORIZED:
                throw new AccessUnauthorizedException();
            case INTERNAL_SERVER_ERROR:
                throw new ServerInternalErrorException();
            case SERVICE_UNAVAILABLE:
                throw new AccessThrottledException();
            default:
                throw new AmazonServiceException();
        }
    }

    private String signUrl(String isbn) {

        Map<String, String> params = new HashMap<>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemLookup");
        params.put("AWSAccessKeyId", amazonProperties.getAccessKeyId());
        params.put("AssociateTag", amazonProperties.getAssociateTag());
        params.put("ItemId", isbn);
        params.put("IdType", "ISBN");
        params.put("ResponseGroup", "Images,ItemAttributes");
        params.put("SearchIndex", "Books");

        return helper.sign(params);
    }

    private AmazonBook parseResponse(String xml) {

        AmazonResponse response = getAmazonResponse(xml);

        if (response.getData().getRequest().hasErrors()) {
            handleErrors(response.getData().getRequest().getErrors());
        }

        if (response.getData().getBooks().size() == 0)  {
            throw new BookNotFoundException(response.getData().getRequest().getItemId());
        }

        return response.getData().getBooks().get(0);
    }

    private AmazonResponse getAmazonResponse(String xml) {

        AmazonResponse response;

        try {
            Serializer serializer = new Persister();
            response = serializer.read(AmazonResponse.class, xml, false);
        } catch (Exception ex) {
            throw new RuntimeException("Deserialize amazon service response error.", ex.getCause());
        }

        return response;
    }

    private void handleErrors(List<Error> errors) {
        for(Error error : errors) {
            handleError(error);
        }
    }

    private void handleError(Error error) {
        String errorCode = error.getCode();
        String errorMsg = error.getMessage();

        if (errorCode.equalsIgnoreCase("AWS.InvalidParameterValue")) {
            throw new IsbnIllegalException(errorMsg);
        }

        throw new RuntimeException(String.format("%s:%s", errorCode, errorMsg));
    }

}
