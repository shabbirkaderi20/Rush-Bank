package com.rush.banking.userservice.exceptions;

public class UserAuthorityForbiddenException extends RuntimeException {

    public UserAuthorityForbiddenException() {
    }
    public UserAuthorityForbiddenException(String message) {
        super(message);
    }
    public UserAuthorityForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserAuthorityForbiddenException(Throwable cause) {
        super(cause);
    }
    public UserAuthorityForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
