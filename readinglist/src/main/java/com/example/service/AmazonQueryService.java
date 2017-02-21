package com.example.service;

import com.example.configuration.AmazonProperties;
import com.example.domain.Book;
import com.example.domain.BookConverter;
import com.example.domain.amazon.AmazonBook;
import com.example.domain.amazon.AmazonErrorResponse;
import com.example.domain.amazon.AmazonResponse;
import com.example.domain.amazon.Error;
import com.example.service.amazon.SignedRequestsHelper;
import com.example.service.exception.AmazonServiceException;
import com.example.service.exception.BookNotFoundException;
import com.example.service.exception.IsbnIllegalException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private AmazonProperties amazonProperties;

    @Autowired
    private RestTemplate template;

    @Autowired
    private SignedRequestsHelper helper;

    @Override
    public Book queryByISBN(String isbn) {
        AmazonBook book = query(isbn);

        return BookConverter.from(book);
    }

    private AmazonBook query(String isbn) {

        String serviceUrl = signUrl(isbn);

        ResponseEntity<String> response = template.getForEntity(URI.create(serviceUrl), String.class);
        HttpStatus status = response.getStatusCode();

        if(RestUtils.isOk(status)) {
            return parseResponse(response.getBody());
        }

        if(RestUtils.isError(status)){
            handleError(getAmazonResponse(response.getBody(), AmazonErrorResponse.class).getError());
        }

        throw new AmazonServiceException("Occurs some unknown error when access amazon web service.");
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

        AmazonResponse response = getAmazonResponse(xml, AmazonResponse.class);

        if (response.getData().getRequest().hasErrors()) {
            handleErrors(response.getData().getRequest().getErrors());
        }

        if (response.getData().getBooks().size() == 0)  {
            throw new BookNotFoundException(response.getData().getRequest().getItemId());
        }

        return response.getData().getBooks().get(0);
    }

    private <T> T getAmazonResponse(String xml, Class<T> type) {
        T result;

        try {
            Serializer serializer = new Persister();
            result = serializer.read(type, xml, false);
        } catch (Exception ex) {
            throw new AmazonServiceException("Deserialize amazon service response error.", ex.getCause());
        }

        return result;
    }

    private void handleErrors(List<Error> errors) {
        for(Error error : errors) {
            handleError(error);
        }
    }

    private void handleError(Error error) {
        if (error.isInvalidIsbn()) {
            throw new IsbnIllegalException(error.toString());
        }

        throw new AmazonServiceException(error.toString());
    }

}
