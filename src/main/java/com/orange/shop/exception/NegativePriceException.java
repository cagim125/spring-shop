package com.orange.shop.exception;

public class NegativePriceException extends RuntimeException{
    public NegativePriceException(String name) {
        super(name);
    }
}
