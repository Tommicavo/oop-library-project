package com.epicode.models;

import com.epicode.models.enums.BookType;
import com.epicode.utils.AppConstants;
import com.epicode.utils.InputValidation;

public abstract class Book {

    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;

    public Book(Object id, String title, String author, Object publicationYear) {
        this.id = InputValidation.validateLong(id, AppConstants.MIN_ID, AppConstants.ID);
        this.title = InputValidation.validateString(title, AppConstants.TITLE);
        this.author = InputValidation.validateString(author, AppConstants.AUTHOR);
        this.publicationYear = InputValidation.validateInteger(publicationYear, AppConstants.MIN_YEAR, AppConstants.PUBLICATION_YEAR);
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    // Abstract methods: they will be implemented by concrete sub-classes
    public abstract BookType getBookType();
    public abstract String printInfo();

    // Other methods
    public String getInfo() {
        return
            "BOOK INFO" + "\n" +
            "- Base Info:" + "\n" +
            "Title: " + title + "\n" +
            "Author: " + author + "\n" +
            "Year: " + publicationYear + "\n";
    }
}
