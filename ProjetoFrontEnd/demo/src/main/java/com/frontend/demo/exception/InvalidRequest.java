package com.frontend.demo.exception;

public class InvalidRequest extends Exception{
    public InvalidRequest() {}

    public InvalidRequest(String message) {
        super(message);
    }
}
