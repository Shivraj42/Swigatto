package com.example.EatHub.exceptions;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message){
        super(message);
    }
}
