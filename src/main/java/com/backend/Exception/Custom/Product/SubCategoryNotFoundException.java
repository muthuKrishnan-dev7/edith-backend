package com.backend.Exception.Custom.Product;

public class SubCategoryNotFoundException extends RuntimeException{

    public SubCategoryNotFoundException(){
        super("Subcategory not found.");
    }

}
