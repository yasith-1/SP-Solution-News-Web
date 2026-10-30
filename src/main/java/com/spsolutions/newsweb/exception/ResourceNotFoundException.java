package com.spsolutions.newsweb.exception;

/**
 * Thrown when a requested domain entity (category, news item, ...) does not exist.
 *
 * <p>Single Responsibility: this type only models the "not found" condition; deciding
 * how to present it to the user is the exception handler's job, not this class's.</p>
 */
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
