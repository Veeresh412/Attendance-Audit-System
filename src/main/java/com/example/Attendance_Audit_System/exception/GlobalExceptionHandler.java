package com.example.Attendance_Audit_System.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // This tells Spring to route all @Valid failures here
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        
        // Loop through all the bad fields and grab the specific message we wrote (like "Must be a valid email")
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Send a clean 400 Bad Request JSON response back to the frontend
        return ResponseEntity.badRequest().body(errors);
    }

    // This tells Spring to route all Database Unique failures here
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseExceptions(DataIntegrityViolationException ex) {
        
        Map<String, String> error = new HashMap<>();
        error.put("databaseError", "That email or phone number is already registered!");
        
        return ResponseEntity.badRequest().body(error);
    }

    // Catch-All for any other crashes so it NEVER returns an HTML error page
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        
        // Check if the crash was caused by a duplicate database entry that slipped through
        if (ex.getMessage() != null && ex.getMessage().contains("duplicate key value")) {
            error.put("databaseError", "That email or phone number is already registered!");
            return ResponseEntity.badRequest().body(error);
        }

        error.put("serverCrash", "An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }
}