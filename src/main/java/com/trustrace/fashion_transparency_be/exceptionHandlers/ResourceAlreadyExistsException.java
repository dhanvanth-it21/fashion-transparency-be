package com.trustrace.fashion_transparency_be.exceptionHandlers;

public class ResourceAlreadyExistsException extends RuntimeException {


    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
