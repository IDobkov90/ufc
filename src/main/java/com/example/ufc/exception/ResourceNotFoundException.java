package com.example.ufc.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Results in HTTP 404 status.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceType, Long id) {
        super(String.format("%s with ID %d not found", resourceType, id));
    }

    public ResourceNotFoundException(String resourceType, String identifier) {
        super(String.format("%s '%s' not found", resourceType, identifier));
    }
}

