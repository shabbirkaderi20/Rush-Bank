package com.rush.banking.userservice.exceptions;

public class AccountActionTakenException extends RuntimeException {

    public AccountActionTakenException() {
    }

    public AccountActionTakenException(String message) {
        super(message);
    }

    public AccountActionTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountActionTakenException(Throwable cause) {
        super(cause);
    }

    public AccountActionTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
