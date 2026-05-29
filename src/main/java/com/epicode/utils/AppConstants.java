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

    // ANSI escape codes for color-coded console logs
    public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static final String INFO = "INFO";
    public static final String CYAN = "\u001B[36m";

    public static final String SUCCESS = "SUCCESS";
    public static final String GREEN = "\u001B[32m";

    public static final String WARN = "WARN";
    public static final String YELLOW = "\u001B[33m";

    public static final String ERROR = "ERROR";
    public static final String RED = "\u001B[31m";

    public static final String RESET = "\u001B[0m";
}
