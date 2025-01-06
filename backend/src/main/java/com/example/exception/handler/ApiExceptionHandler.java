package com.example.exception.handler;

import com.example.exception.AccessForbiddenException;
import com.example.exception.AlreadyInPlaylistException;
import com.example.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Class for handling exceptions in the API.
 * Handles exceptions related to access, missing entities, and already added items in a playlist.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Exception handler for when an entity is not found.
     *
     * @param exception the exception related to the missing entity.
     * @return ResponseEntity with an error message and a 404 status.
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for when access is forbidden.
     *
     * @param exception the exception related to access restriction.
     * @return ResponseEntity with an error message and a 403 status.
     */
    @ExceptionHandler(value = AccessForbiddenException.class)
    public ResponseEntity<Map<String, String>> handleAccessForbiddenException(AccessForbiddenException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    /**
     * Exception handler for when an item is already in the playlist.
     *
     * @param exception the exception related to adding an item to the playlist.
     * @return ResponseEntity with an error message and a 409 status.
     */
    @ExceptionHandler(value = AlreadyInPlaylistException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyInPlaylistException(AlreadyInPlaylistException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.CONFLICT);
    }
}
