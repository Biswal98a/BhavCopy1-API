package com.BhavCopy_1.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceeptionHandler {
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Resource not found");
	        errorResponse.put("details", ex.getMessage());

	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	    }

}
