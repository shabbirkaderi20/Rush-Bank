package com.rush.banking.authorityservice.exception;

public class NoAuthorityFoundException extends RuntimeException{

    public NoAuthorityFoundException() {
    }
    public NoAuthorityFoundException(String message) {
        super(message);
    }
    public NoAuthorityFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoAuthorityFoundException(Throwable cause) {
        super(cause);
    }
    public NoAuthorityFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
