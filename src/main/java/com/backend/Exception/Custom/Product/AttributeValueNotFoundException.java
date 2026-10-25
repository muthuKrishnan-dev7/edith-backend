package com.backend.Exception.Custom.Product;

public class AttributeValueNotFoundException extends RuntimeException{

    public AttributeValueNotFoundException(){
        super("Attribute Value not found.");
    }

}
