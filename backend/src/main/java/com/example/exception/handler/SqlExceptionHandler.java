package com.example.exception.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Class for handling exceptions related to data integrity violations in the database.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class SqlExceptionHandler {

    /**
     * Exception handler for data integrity violations in the database.
     * For example, when attempting to insert a duplicate value or violating foreign key constraints.
     *
     * @param exception the DataIntegrityViolationException.
     * @return ResponseEntity with an error message and a 400 (BAD_REQUEST) status.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", exception.getMessage()));
    }

}
