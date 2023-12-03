package com.example.EatHub.exceptions;

public class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException(String message){super(message);}
}
