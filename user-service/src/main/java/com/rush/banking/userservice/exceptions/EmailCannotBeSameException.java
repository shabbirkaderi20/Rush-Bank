package com.rush.banking.userservice.exceptions;

public class EmailCannotBeSameException extends RuntimeException {

    public EmailCannotBeSameException() {
    }

    public EmailCannotBeSameException(String message) {
        super(message);
    }

    public EmailCannotBeSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailCannotBeSameException(Throwable cause) {
        super(cause);
    }

    public EmailCannotBeSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
