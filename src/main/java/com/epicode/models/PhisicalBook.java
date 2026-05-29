package com.epicode.models;

import com.epicode.models.enums.BookType;
import com.epicode.utils.InputValidation;

public class PhisicalBook extends Book {

    private Integer pages;

    public PhisicalBook(Object id, String title, String author, Object publicationYear, Object pages) {
        super(id, title, author, publicationYear);
        this.pages = InputValidation.validateInteger(pages, 1, "Pages");
    }

    // Getter and Setter
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    // Parent class abstract method implementation
    @Override
    public BookType getBookType() {
        return BookType.PHYSICAL_BOOK;
    }

    @Override
    public String printInfo() {
        return super.getInfo() +
            "- Addition Info:" + "\n" +
            "Book Type: '" + getBookType() + "'\n" +
            "Pages: " + pages;
    }
}
