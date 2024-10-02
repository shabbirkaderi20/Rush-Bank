package com.rush.banking.userservice.exceptions;

public class EmailAlreadyTakenException extends RuntimeException{

    public EmailAlreadyTakenException() {
    }
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
    public EmailAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
    public EmailAlreadyTakenException(Throwable cause) {
        super(cause);
    }
    public EmailAlreadyTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
