package com.example.exception;

/**
 * Exception thrown when an entity (e.g., user, album, or song)
 * cannot be found in the database or system.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructor that accepts an error message.
     *
     * @param message the message that will be passed to the parent constructor.
     */
    public EntityNotFoundException(String message) {
        super(message);  // Call the parent class constructor with the error message
    }
}
