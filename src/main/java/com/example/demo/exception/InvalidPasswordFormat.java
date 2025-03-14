package com.example.demo.exception;

public class InvalidPasswordFormat extends RuntimeException {
    public InvalidPasswordFormat(String message) {
        super(message);
    }
}
