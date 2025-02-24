package com.trustrace.tiles_hub_be.exceptionHandlers;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
