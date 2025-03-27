package com.trustrace.fashion_transparency_be.exceptionHandlers;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
