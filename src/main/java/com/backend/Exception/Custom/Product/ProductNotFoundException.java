package com.backend.Exception.Custom.Product;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
        super("Product Not Found");
    }

}
