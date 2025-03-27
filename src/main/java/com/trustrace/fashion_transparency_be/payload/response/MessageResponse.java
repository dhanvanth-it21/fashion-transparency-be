package com.trustrace.fashion_transparency_be.payload.response;

public class MessageResponse {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
