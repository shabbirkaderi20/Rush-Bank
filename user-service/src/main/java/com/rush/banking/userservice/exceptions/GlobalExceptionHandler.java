package com.rush.banking.userservice.exceptions;

import com.rush.banking.userservice.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PasswordSyntaxIncorrectException.class)
    public ResponseEntity<Object> handlePasswordSyntaxIncorrectException(PasswordSyntaxIncorrectException exception) {
        return new ResponseEntity<>(Constants.PASSWORD_SYNTAX_INCORRECT, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserNameAlreadyExistsException(UserNameAlreadyExistsException exception) {
        return new ResponseEntity<>(Constants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<Object> handleEmailAlreadyTakenException(EmailAlreadyTakenException exception) {
        return new ResponseEntity<>(Constants.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContactAlreadyExistsException.class)
    public ResponseEntity<Object> handleContactAlreadyExistsException(ContactAlreadyExistsException exception) {
        return new ResponseEntity<>(Constants.CONTACT_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordCannotBeSameException.class)
    public ResponseEntity<Object> handlePasswordCannotBeSameException(PasswordCannotBeSameException exception) {
        return new ResponseEntity<>(Constants.PASSWORD_CANNOT_BE_SAME, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<Object> handleRequestNotFoundException(RequestNotFoundException exception) {
        return new ResponseEntity<>(Constants.REQUEST_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAuthorityForbiddenException.class)
    public ResponseEntity<Object> handleUserAuthorityForbiddenException(UserAuthorityForbiddenException exception) {
        return new ResponseEntity<>(Constants.FORBIDDEN_USER_AUTHORITY, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserRequestIsNotAuthorized.class)
    public ResponseEntity<Object> handleUserRequestIsNotAuthorized(UserRequestIsNotAuthorized exception) {
        return new ResponseEntity<>(Constants.FORBIDDEN_REQUEST, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OperationCannotBePerformedException.class)
    public ResponseEntity<Object> handleOperationCannotBePerformedException(OperationCannotBePerformedException exception) {
        return new ResponseEntity<>(Constants.FORBIDDEN_REQUEST_REVOKED, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<Object> handleIncorrectCredentialsException(IncorrectCredentialsException exception) {
        return new ResponseEntity<>(Constants.INCORRECT_CREDENTIALS, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(DomainIncorrectException.class)
    public ResponseEntity<Object> handleDomainIncorrectException(DomainIncorrectException exception) {
        return new ResponseEntity<>(Constants.DOMAIN_NOT_AUTHORIZED, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<Object> handlePasswordIncorrectException(PasswordIncorrectException exception) {
        return new ResponseEntity<>(Constants.PASSWORD_INCORRECT, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OldContactRepeatedException.class)
    public ResponseEntity<Object> handleOldContactRepeatedException(OldContactRepeatedException exception) {
        return new ResponseEntity<>(Constants.CONTACT_CANNOT_BE_SAME, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameBaseLocationException.class)
    public ResponseEntity<Object> handleSameBaseLocationException(SameBaseLocationException exception) {
        return new ResponseEntity<>(Constants.BASE_LOCATION_CANNOT_BE_SAME, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailCannotBeSameException.class)
    public ResponseEntity<Object> handleEmailCannotBeSameException(EmailCannotBeSameException exception) {
        return new ResponseEntity<>(Constants.EMAIL_CANNOT_BE_SAME, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleRequestAlreadyRegisteredException(RequestAlreadyRegisteredException exception) {
        return new ResponseEntity<>(Constants.REQUEST_ALREADY_REGISTERED, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountActionTakenException.class)
    public ResponseEntity<Object> handleAccountActionTakenException(AccountActionTakenException exception) {
        return new ResponseEntity<>(Constants.ACCOUNT_ACTION_TAKEN, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<Object> handleEmailNotVerifiedException(EmailNotVerifiedException exception) {
        return new ResponseEntity<>(Constants.EMAIL_NOT_VERIFIED, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberNotVerifiedException.class)
    public ResponseEntity<Object> handlePhoneNumberNotVerifiedException(PhoneNumberNotVerifiedException exception) {
        return new ResponseEntity<>(Constants.PHONE_NUMBER_NOT_VERIFIED, HttpStatus.BAD_REQUEST);
    }
}
