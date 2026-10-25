package com.backend.Exception.Custom.Product;

public class ImageNotFoundException extends RuntimeException{

    public ImageNotFoundException(){
        super("Image not found");
    }

}
