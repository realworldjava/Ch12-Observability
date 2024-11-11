package com.wiley.realworldjava.observability.exception;

public class MortgageException extends RuntimeException{

  public MortgageException(String message) {
    super(message);
  }

  public MortgageException(String message, Throwable cause) {
    super(message, cause);
  }
}
