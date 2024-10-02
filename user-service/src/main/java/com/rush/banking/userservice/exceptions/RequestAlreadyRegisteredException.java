package com.rush.banking.userservice.exceptions;

public class RequestAlreadyRegisteredException extends RuntimeException {

    public RequestAlreadyRegisteredException() {
    }

    public RequestAlreadyRegisteredException(String message) {
        super(message);
    }

    public RequestAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }

    public RequestAlreadyRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
