package com.backend.Exception.Custom.Product;

public class ColorNotFoundException extends RuntimeException{

    public ColorNotFoundException(){
        super("Color not found");
    }

}
