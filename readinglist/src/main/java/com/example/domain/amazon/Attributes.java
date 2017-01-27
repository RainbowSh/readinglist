package com.example.domain.amazon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Rainbow on 2017/1/27.
 */
@Root
public class Attributes {

    @Element(name = "Title")
    private String title;

    @Element(name = "Author")
    private String author;

    @Element(name = "Binding")
    private String binding;

    @Element(name = "Creator")
    private String creator;

    @Element(name = "Edition")
    private String edition;

    @Element(name = "ISBN")
    private String isbn;

    @Element(name = "NumberOfPages")
    private String numberOfPages;

    @Element(name = "Publisher")
    private String publisher;

    @Element(name = "PublicationDate")
    private String PublicationDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationDate() {
        return PublicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        PublicationDate = publicationDate;
    }
}
