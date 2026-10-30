package com.spsolutions.newsweb.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Convenience factory producing a consistent message for a missing entity by id.
     */
    public static ResourceNotFoundException of(String resourceName, Object id) {
        return new ResourceNotFoundException(resourceName + " not found with id: " + id);
    }
}
