package com.example.exception.handler;

import com.example.exception.AccessForbiddenException;
import com.example.exception.AlreadyInPlaylistException;
import com.example.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AccessForbiddenException.class)
    public ResponseEntity<Map<String,String>> handleAccessForbiddenException(AccessForbiddenException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = AlreadyInPlaylistException.class)
    public ResponseEntity<Map<String,String>> handleAlreadyInPlaylistException(AlreadyInPlaylistException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.CONFLICT);
    }
}