package com.example.exception;

/**
 * Exception thrown when access is forbidden.
 * Typically used when a user attempts to perform an action they are not allowed to (e.g., access to a resource).
 */
public class AccessForbiddenException extends RuntimeException {

    /**
     * Constructor that accepts an error message.
     *
     * @param message the error message that will be displayed to the user.
     */
    public AccessForbiddenException(String message) {
        super(message);  // Call the parent class constructor with the error message
    }
}
