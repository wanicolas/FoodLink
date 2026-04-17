package com.foodlink.orders.presentation.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), java.time.Instant.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
