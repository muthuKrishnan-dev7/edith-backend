package com.backend.Exception.Custom;

public class ProductVariantNotFoundException extends RuntimeException{

    public ProductVariantNotFoundException(){
        super("Product Not Found");
    }

}
