package com.rush.banking.userservice.exceptions;

public class PasswordCannotBeSameException extends RuntimeException {

    public PasswordCannotBeSameException() {
    }
    public PasswordCannotBeSameException(String message) {
        super(message);
    }
    public PasswordCannotBeSameException(String message, Throwable cause) {
        super(message, cause);
    }
    public PasswordCannotBeSameException(Throwable cause) {
        super(cause);
    }
    public PasswordCannotBeSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
