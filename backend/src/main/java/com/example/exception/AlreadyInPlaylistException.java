package com.example.exception;

public class AlreadyInPlaylistException extends RuntimeException {
    public AlreadyInPlaylistException(String message) {
        super(message);
    }
}
