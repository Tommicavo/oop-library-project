package com.epicode.utils;

public class AppConstants {

    private AppConstants() {}

    // Book Types
    public static final String AUDIO_BOOK = "AUDIO-BOOK";
    public static final String PHISICAL_BOOK = "PHISICAL-BOOK";

    // General Constraint
    public static final Integer MIN_YEAR = 0;
    public static final Long MIN_ID = 1L;

    // Book Field Keys
    public static final String ID = "ID";
    public static final String TITLE = "Title";
    public static final String AUTHOR = "Author";
    public static final String PUBLICATION_YEAR = "Publication Year";

    // AudioBook Field Keys
    public static final String NARRATION_MINUTES = "Narration Minutes";

    // PhisicalBook Field Keys
    public static final String PAGES = "Pages";
}
