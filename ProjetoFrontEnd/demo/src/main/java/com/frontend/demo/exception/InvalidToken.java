package com.frontend.demo.exception;

public class InvalidToken extends Exception{
    public InvalidToken() {}

    public InvalidToken(String message) {
        super(message);
    }
}
