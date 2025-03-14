package com.example.demo.exception;

public class InvalidEmailFormat extends RuntimeException {
    public InvalidEmailFormat(String message) {
        super(message);
    }
}