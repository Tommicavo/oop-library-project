package com.epicode.models;

public abstract class Book {

    private Long id;
    private String title;
    private String author;
    private Integer publicationYear;

    public Book(Long id, String title, String author, Integer publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
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
    public abstract String getBookType();
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
