package com.rush.banking.userservice.exceptions;

public class OldContactRepeatedException extends RuntimeException {

    public OldContactRepeatedException() {
    }

    public OldContactRepeatedException(String message) {
        super(message);
    }

    public OldContactRepeatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OldContactRepeatedException(Throwable cause) {
        super(cause);
    }

    public OldContactRepeatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
