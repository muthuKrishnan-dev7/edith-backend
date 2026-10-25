package com.backend.Exception.Handler;

import com.backend.Exception.Custom.*;
import com.backend.Exception.Custom.Product.*;
import com.backend.Exception.Custom.Store.StoreNotActiveException;
import com.backend.Exception.Custom.Store.StoreNotFoundException;
import com.backend.Exception.Custom.User.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
//            =================== Store Exceptions ===================
            StoreNotFoundException.class,
            StoreNotActiveException.class,
//            =================== User Exceptions ===================
            UserNotFoundException.class,
//            =================== Product Exceptions ===================
            ProductNotFoundException.class,
            ProductVariantNotFoundException.class,
            CategoryNotFoundException.class,
            SubCategoryNotFoundException.class,
            AttributeTypeNotFoundException.class,
            AttributeValueNotFoundException.class,
            GenderNotFoundException.class,
            FeatureNotFoundException.class,
            MaterialNotFoundException.class,
            ColorNotFoundException.class,
            ImageNotFoundException.class,
//            =================== Order Exceptions ===================
            OrderNotFoundException.class,
            StorePaymentNotFound.class,
            OrderAlreadyPaidException.class,

    })
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

}
