package com.epicode.exceptions;

// Custom Exception on wrong BookType
public class IllegalBookTypeException extends IllegalArgumentException {
    public IllegalBookTypeException(String message) {
        super(message);
    }
}
