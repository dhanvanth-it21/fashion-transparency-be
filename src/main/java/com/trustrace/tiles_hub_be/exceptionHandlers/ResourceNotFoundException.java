package com.trustrace.tiles_hub_be.exceptionHandlers;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
