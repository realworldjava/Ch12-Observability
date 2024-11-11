package com.wiley.realworldjava.observability.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleException(MortgageException exception) {
    ErrorResponse errorResponse = ErrorResponse.builder(exception, HttpStatusCode.valueOf(400), exception.getMessage()).build();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
