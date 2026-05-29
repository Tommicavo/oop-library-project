package com.epicode.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppLogger {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConstants.DATE_TIME_FORMATTER);

    public static void info(String message) {
        log(AppConstants.INFO, AppConstants.CYAN, message);
    }

    public static void success(String message) {
        log(AppConstants.SUCCESS, AppConstants.GREEN, message);
    }

    public static void warn(String message) {
        log(AppConstants.WARN, AppConstants.YELLOW, message);
    }

    public static void error(String message) {
        log(AppConstants.ERROR, AppConstants.RED, message);
    }

    private static void log(String level, String color, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.printf("[%s] %s[%-7s]%s %s%n", timestamp, color, level, AppConstants.RESET, message);
    }
}
