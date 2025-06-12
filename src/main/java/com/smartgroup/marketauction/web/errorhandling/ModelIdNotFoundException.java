package com.smartgroup.marketauction.web.errorhandling;

public class ModelIdNotFoundException extends RuntimeException {
    public ModelIdNotFoundException(String message) {
        super(message);
    }
}
