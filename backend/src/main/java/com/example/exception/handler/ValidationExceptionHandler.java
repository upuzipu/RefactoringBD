package com.example.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Exception handler for validation errors.
 * Handles exceptions that occur due to incorrect input data, such as violations of validation annotations
 * (e.g., @NotBlank, @Size) or data format mismatches.
 */
@RestControllerAdvice
public class ValidationExceptionHandler {

    /**
     * Handler for exceptions occurring due to invalid method arguments (e.g., violations of validation annotations).
     *
     * @param exception MethodArgumentNotValidException exception.
     * @return ResponseEntity with validation errors and a 400 (BAD_REQUEST) status.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException
    (MethodArgumentNotValidException exception) {
        Map<String, List<String>> body = new HashMap<>();
        body.put("errors",
                exception.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)  // Get error message
                        .filter(Objects::nonNull)  // Filter out null values
                        .filter(s -> !s.isBlank())  // Filter out empty strings
                        .toList());  // Collect errors into a list

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);  // Return response with errors and 400 status
    }

    /**
     * Handler for exceptions occurring due to constraint violations at the object level.
     *
     * @param ex ConstraintViolationException exception.
     * @return ResponseEntity with validation errors and a 400 (BAD_REQUEST) status.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolationException
    (ConstraintViolationException ex) {
        Map<String, List<String>> body = new HashMap<>();
        body.put("errors",
                ex.getConstraintViolations().stream()  // Iterate through all constraint violations
                        .map(ConstraintViolation::getMessage)  // Get error message
                        .filter(Objects::nonNull)  // Filter out null values
                        .filter(s -> !s.isBlank())  // Filter out empty strings
                        .toList());  // Collect errors into a list

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);  // Return response with errors and 400 status
    }
}
