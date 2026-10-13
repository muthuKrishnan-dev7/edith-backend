package com.backend.Exception.Custom;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
        super("Product Not Found");
    }

}
