package com.epicode.utils;

public class InputValidation {

    public static String validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null, empty, or blank.");
        }
        return value.trim();
    }

    public static Integer validateInteger(Object value, int minValue, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }

        int parsedValue;

        if (value instanceof Integer) {
            parsedValue = (Integer) value;
        } 
        else {
            String stringValue = value.toString().trim();
            if (stringValue.isEmpty()) {
                throw new IllegalArgumentException(fieldName + " cannot be an empty string.");
            }
            try {
                parsedValue = Integer.parseInt(stringValue);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(fieldName + " must be a valid whole number. Received invalid value: '" + stringValue + "'");
            }
        }

        if (parsedValue < minValue) {
            throw new IllegalArgumentException(fieldName + " must be at least " + minValue + ".");
        }

        return parsedValue;
    }

    public static Long validateLong(Object value, long minValue, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }

        long parsedValue;

        if (value instanceof Long) {
            parsedValue = (Long) value;
        } 
        else if (value instanceof Integer) {
            parsedValue = ((Integer) value).longValue();
        }
        else {
            String stringValue = value.toString().trim();
            if (stringValue.isEmpty()) {
                throw new IllegalArgumentException(fieldName + " cannot be an empty string.");
            }
            try {
                parsedValue = Long.parseLong(stringValue);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(fieldName + " must be a valid long integer. Received invalid value: '" + stringValue + "'");
            }
        }

        if (parsedValue < minValue) {
            throw new IllegalArgumentException(fieldName + " must be at least " + minValue + ".");
        }

        return parsedValue;
    }
}
