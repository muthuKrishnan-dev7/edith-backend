package com.backend.Exception.Custom.Product;

public class ProductVariantNotFoundException extends RuntimeException{

    public ProductVariantNotFoundException(){
        super("Product Not Found");
    }

}
