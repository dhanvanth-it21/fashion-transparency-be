package com.trustrace.tiles_hub_be.exceptionHandlers;

public class ResourceAlreadyExistsException extends RuntimeException {


    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
