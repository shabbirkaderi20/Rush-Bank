package com.rush.banking.userservice.exceptions;

public class PhoneNumberNotVerifiedException extends RuntimeException {

    public PhoneNumberNotVerifiedException() {
    }

    public PhoneNumberNotVerifiedException(String message) {
        super(message);
    }

    public PhoneNumberNotVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNumberNotVerifiedException(Throwable cause) {
        super(cause);
    }

    public PhoneNumberNotVerifiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
