package org.example.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.exceptions.BrandNotFoundException;
import org.example.backend.exceptions.MyErrorResponse;
import org.example.backend.exceptions.SaleItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(SaleItemNotFoundException.class)
    public ResponseEntity<MyErrorResponse> handleSaleItemNotFoundException(
            SaleItemNotFoundException ex , HttpServletRequest request
    ){
        MyErrorResponse myErrorResponse = new MyErrorResponse(
                HttpStatus.NOT_FOUND.value() , "Not Found", ex.getMessage() , request.getRequestURI()
        );
        return ResponseEntity.status(myErrorResponse.getStatus()).body(myErrorResponse);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<MyErrorResponse> handleBrandNotFoundException(
            BrandNotFoundException ex , HttpServletRequest request
    ){
        MyErrorResponse myErrorResponse = new MyErrorResponse(
                HttpStatus.NOT_FOUND.value() , "Not Found", ex.getMessage() , request.getRequestURI()
        );
        return ResponseEntity.status(myErrorResponse.getStatus()).body(myErrorResponse);
    }

}
