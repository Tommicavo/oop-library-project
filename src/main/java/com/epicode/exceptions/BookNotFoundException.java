package com.epicode.exceptions;

// Custom Book 404 Exception
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
