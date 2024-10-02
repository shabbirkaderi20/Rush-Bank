package com.rush.banking.userservice.exceptions;

public class OperationCannotBePerformedException extends RuntimeException {

    public OperationCannotBePerformedException() {
    }
    public OperationCannotBePerformedException(String message) {
        super(message);
    }
    public OperationCannotBePerformedException(String message, Throwable cause) {
        super(message, cause);
    }
    public OperationCannotBePerformedException(Throwable cause) {
        super(cause);
    }
    public OperationCannotBePerformedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
