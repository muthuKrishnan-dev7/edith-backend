package com.backend.Exception.Custom.Product;

public class CategoryNotFoundException extends RuntimeException{

    public  CategoryNotFoundException(){
        super("Selected category not found.");
    }

}
