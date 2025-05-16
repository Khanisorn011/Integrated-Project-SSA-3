package org.example.backend.exceptions;

public class BrandNotFoundException extends RuntimeException {
  public BrandNotFoundException(String message) {
    super(message);
  }
}
