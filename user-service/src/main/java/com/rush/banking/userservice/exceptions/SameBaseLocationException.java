package com.rush.banking.userservice.exceptions;

public class SameBaseLocationException extends RuntimeException {

    public SameBaseLocationException() {
    }

    public SameBaseLocationException(String message) {
        super(message);
    }

    public SameBaseLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameBaseLocationException(Throwable cause) {
        super(cause);
    }

    public SameBaseLocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
