package com.frontend.demo.util;

public class ResponseMessage {
    private boolean validToken;
    private boolean correctResponse;
    private Object response;

    public ResponseMessage()
    {
        validToken=false;
        correctResponse=false;
        response=null;
    }
    public ResponseMessage(boolean validToken, boolean correctResponse, Object response) {
        this.validToken = validToken;
        this.correctResponse = correctResponse;
        this.response = response;
    }

    public boolean isValidToken() {
        return validToken;
    }

    public void setValidToken(boolean validToken) {
        this.validToken = validToken;
    }

    public boolean isCorrectResponse() {
        return correctResponse;
    }

    public void setCorrectResponse(boolean correctResponse) {
        this.correctResponse = correctResponse;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
