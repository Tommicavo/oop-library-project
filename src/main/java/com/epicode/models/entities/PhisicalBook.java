package com.epicode.models.entities;

import com.epicode.models.enums.BookType;
import com.epicode.utils.AppConstants;
import com.epicode.utils.InputValidation;

public class PhisicalBook extends Book {

    private Integer pages;
    private boolean isHardCover;

    public PhisicalBook(Object id, String title, String author, Integer publicationYear, Integer pages, boolean isHardCover) {
        super(id, title, author, publicationYear);
        this.pages = InputValidation.validateInteger(pages, 1, AppConstants.PAGES);
        this.isHardCover = isHardCover;
    }

    // Getter and Setter
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public boolean isHardCover() {
        return isHardCover;
    }

    public void setHardCover(boolean isHardCover) {
        this.isHardCover = isHardCover;
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
