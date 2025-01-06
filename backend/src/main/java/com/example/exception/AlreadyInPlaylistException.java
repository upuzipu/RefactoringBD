package com.example.exception;

/**
 * Exception thrown when an object (e.g., a song) is already added to a playlist.
 * Used to prevent adding the same item to a playlist multiple times.
 */
public class AlreadyInPlaylistException extends RuntimeException {

    /**
     * Constructor that accepts an error message.
     *
     * @param message the message that will be passed to the parent constructor.
     */
    public AlreadyInPlaylistException(String message) {
        super(message);  // Call the parent class constructor with the error message
    }
}
