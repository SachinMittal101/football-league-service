package com.sapient.demo.footballleague.util;

public class NotFoundException extends Exception {

    private String message;

    public NotFoundException(String message) {
        super(message);
    }
}
