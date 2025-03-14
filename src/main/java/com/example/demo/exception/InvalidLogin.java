package com.example.demo.exception;

public class InvalidLogin extends RuntimeException {
    public InvalidLogin(String message) {
        super(message);
    }
}
