package com.example.demo.exception;

public class UsernameAlreadyInUse extends RuntimeException {
    public UsernameAlreadyInUse(String message) {
        super(message);
    }
}
