package com.rush.banking.userservice.exceptions;

public class UserRequestIsNotAuthorized extends RuntimeException {

    public UserRequestIsNotAuthorized() {
    }
    public UserRequestIsNotAuthorized(String message) {
        super(message);
    }
    public UserRequestIsNotAuthorized(String message, Throwable cause) {
        super(message, cause);
    }
    public UserRequestIsNotAuthorized(Throwable cause) {
        super(cause);
    }
    public UserRequestIsNotAuthorized(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
