package com.rush.banking.userservice.exceptions;

public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException() {
    }
    public RequestNotFoundException(String message) {
        super(message);
    }
    public RequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public RequestNotFoundException(Throwable cause) {
        super(cause);
    }
    public RequestNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
