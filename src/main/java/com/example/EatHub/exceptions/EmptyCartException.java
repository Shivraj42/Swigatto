package com.example.EatHub.exceptions;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException(String message){super(message);}
}
