package com.smartgroup.marketauction.web.errorhandling.exceptions;

public class ModelIdNotFoundException extends RuntimeException {
    public ModelIdNotFoundException(String message) {
        super(message);
    }
}
