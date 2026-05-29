package com.epicode.models;

public class PhisicalBook extends Book {

    private Integer pages;

    public PhisicalBook(Long id, String title, String author, Integer publicationYear, Integer pages) {
        super(id, title, author, publicationYear);
        this.pages = pages;
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
    public String getBookType() {
        return "PHISICAL-BOOK";
    }

    @Override
    public String printInfo() {
        return super.getInfo() +
            "- Addition Info:" + "\n" +
            "Book Type: '" + getBookType() + "'\n" +
            "Pages: " + pages;
    }
}
