package com.myCompany.performance.exception;

import com.myCompany.performance.dto.EmployeeListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;

/**
 * Global exception handler for the application
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle method argument type mismatch exceptions, particularly for date conversion errors
     * 
     * @param ex The exception
     * @return An empty list with 200 OK status
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<EmployeeListDTO>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Log the exception
        System.err.println("Parameter conversion error: " + ex.getMessage());
        
        // Return an empty list with 200 OK status
        return ResponseEntity.ok(Collections.emptyList());
    }
    
    /**
     * Handle all other exceptions
     * 
     * @param ex The exception
     * @return An empty list with 200 OK status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<EmployeeListDTO>> handleAllExceptions(Exception ex) {
        // Log the exception
        System.err.println("Unexpected error: " + ex.getMessage());
        ex.printStackTrace();
        
        // Return an empty list with 200 OK status
        return ResponseEntity.ok(Collections.emptyList());
    }
}
