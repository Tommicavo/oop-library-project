package com.epicode.models.entities;

import com.epicode.models.enums.BookType;
import com.epicode.utils.AppConstants;
import com.epicode.utils.InputValidation;

public class AudioBook extends Book {

    private Integer narrationMinutes;

    public AudioBook(Object id, String title, String author, Integer publicationYear, Integer narrationMinutes) {
        super(id, title, author, publicationYear);
        this.narrationMinutes = InputValidation.validateInteger(narrationMinutes, 1, AppConstants.NARRATION_MINUTES);
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
    public BookType getBookType() {
        return BookType.AUDIO_BOOK;
    }

    @Override
    public String printInfo() {
        return super.getInfo() +
            "- Addition Info:" + "\n" +
            "Book Type: '" + getBookType() + "'\n" +
            "Duration: " + narrationMinutes + " min";
    }
}
