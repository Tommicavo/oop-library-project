package com.epicode.models;

public class AudioBook extends Book {

    private Integer narrationMinutes;

    public AudioBook(Long id, String title, String author, Integer publicationYear, Integer narrationMinutes) {
        super(id, title, author, publicationYear);
        this.narrationMinutes = narrationMinutes;
    }

    // Getter and Setter
    public Integer getNarrationMinutes() {
        return narrationMinutes;
    }

    public void setNarrationMinutes(Integer narrationMinutes) {
        this.narrationMinutes = narrationMinutes;
    }

    // Parent class abstract method implementation
    @Override
    public String getBookType() {
        return "AUDIO-BOOK";
    }

    @Override
    public String printInfo() {
        return super.getInfo() +
            "- Addition Info:" + "\n" +
            "Book Type: '" + getBookType() + "'\n" +
            "Duration: " + narrationMinutes + " min";
    }
}
