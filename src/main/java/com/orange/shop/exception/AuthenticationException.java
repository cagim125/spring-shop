package com.orange.shop.exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String name) {
        super(name);
    }
}
