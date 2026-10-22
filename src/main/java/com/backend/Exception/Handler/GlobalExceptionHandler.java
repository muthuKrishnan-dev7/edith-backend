package com.backend.Exception.Handler;

import com.backend.Exception.Custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            StoreNotFoundException.class,
            UserNotFoundException.class,
            ProductNotFoundException.class,
            ProductVariantNotFoundException.class,
            OrderNotFoundException.class,
            StorePaymentNotFound.class,
            OrderAlreadyPaidException.class
    })
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

}
