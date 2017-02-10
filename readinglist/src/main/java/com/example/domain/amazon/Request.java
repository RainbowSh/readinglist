package com.example.domain.amazon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rainbow on 2017/2/5.
 */
@Root
public class Request {

    @Element(name = "IsValid", required = false)
    private boolean valid;

    @Element(name = "ItemId", required = false)
    @Path(value = "ItemLookupRequest")
    private String itemId;

    @ElementList(name = "Errors", required = false, type = Error.class)
    private List<Error> errors;

    public List<Error> getErrors() {
        return errors;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean hasErrors() {
        return errors != null;
    }

    public String getItemId() {
        return itemId;
    }
}
