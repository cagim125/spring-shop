package com.orange.shop.exception;

public class TitleTooLongException extends RuntimeException{
    public TitleTooLongException(String message) {
        super(message);
    }
}
