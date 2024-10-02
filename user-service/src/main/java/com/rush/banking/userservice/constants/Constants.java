package com.rush.banking.userservice.constants;

public class Constants {

    private Constants() {}

    public static final String SECRET = "rush9421";

    public static final String PHONE_NUMBER = "VM-RUSHBI";
    public static final String ACCOUNT_SID = "AC32a3c49700934481addd5ce1659f04d2";
    public static final String AUTH_TOKEN = "{{ auth_token }}";

    public static final String PAGE_NUMBER="0";
    public static final String PAGE_SIZE="5";
    public static final String SORT_BY="userId";
    public static final String SORT_DIR="ASC";

    public static final String PASSWORD_SYNTAX_INCORRECT= "Password should be greater than 8, must have a Capital and a character.";
    public static final String USER_ALREADY_EXISTS = "Username is already taken";
    public static final String EMAIL_ALREADY_EXISTS = "Email is already taken";
    public static final String CONTACT_ALREADY_EXISTS = "Contact already Exists";
    public static final String USER_NOT_FOUND = "User with given credentials not found";
    public static final String REQUEST_NOT_FOUND = "Request with request Id not found";
    public static final String FORBIDDEN_USER_AUTHORITY = "User not permitted to process the following request";
    public static final String FORBIDDEN_REQUEST = "User is cannot be permitted to request for change of authority";
    public static final String FORBIDDEN_REQUEST_REVOKED = "The request operation cannot be performed on the following user.";
    public static final String INCORRECT_CREDENTIALS = "Incorrect credentials";
    public static final String DOMAIN_NOT_AUTHORIZED = "Domain is unmatched";
    public static final String PASSWORD_INCORRECT = "Incorrect Password";
    public static final String CONTACT_CANNOT_BE_SAME = "Phone number cannot be the same as previous one";
    public static final String BASE_LOCATION_CANNOT_BE_SAME = "Base location cannot be as the previous one";
    public static final String EMAIL_CANNOT_BE_SAME = "Email Id. cannot be as the previous one";
    public static final String REQUEST_ALREADY_REGISTERED = "The request is already registered";
    public static final String ACCOUNT_ACTION_TAKEN = "Necessary action previously completed cannot revert";
    public static final String EMAIL_NOT_VERIFIED = "E-mail Id not verified";
    public static final String PHONE_NUMBER_NOT_VERIFIED = "Contact number not verified";

    public static final String PASSWORD_CANNOT_BE_SAME = "Current password cannot be same as old password";
    public static final String UPDATE_EMAIL_STRING= "Email changed successfully to ";
    public static final String UPDATE_PASSWORD_STRING= "Password changed successfully to ";
    public static final String UPDATE_PHONE_NUMBER_STRING= "Contact no. changed successfully to ";
    public static final String BASE_LOCATION_REQUEST_REGISTERED_STRING = "Base location request has been registered. Requested location: ";
    public static final String BASE_LOCATION_REQUEST_SUCCESSFUL = "Your response request to change the Base location is Approved.\nYour base location changed to:";
    public static final String BASE_LOCATION_REQUEST_FAILED = "Your response request to change the Base location is denied";
    public static final String AUTHORITY_RAISE_REQUEST_REGISTERED_STRING = "Authority promotion request to MASTER-CLIENT has been registered.";
    public static final String MASTER_USER_REGISTRATION_REQUEST_REGISTERED_STRING = "Account registration request to MASTER-CLIENT has been raised.";
    public static final String MASTER_USER_REGISTRATION_REQUEST_SUCCESSFUL_STRING = "User registered Successfully !!!";
    public static final String MASTER_USER_REGISTRATION_DENIED = "Master account registration request has been denied for :";
    public static final String AUTHORITY_RAISE_REQUEST_SUCCESSFUL_STRING = "Authority raised request successfully";
    public static final String AUTHORITY_RAISE_REQUEST_DENIED_STRING = "Authority raised request denied";
    public static final String DEGRADE_AUTHORITY_STRING = "The user authority demoted for user: ";
    public static final String ACCOUNT_CLOSE_REQUEST_SUBMITTED_STRING = "Your account closure request is submitted";
    public static final String ACCOUNT_CLOSED_RESPONSE_SUCCESSFUL_STRING = "Account closed successfully";
    public static final String ACCOUNT_CLOSED_RESPONSE_REJECTED_STRING = "Account closing request rejected";

    public static final String EMAIL_FROM = "noreply@rushcorp.inc";
    public static final String EMAIL_START_WARNING = "(do not reply. This is a system generated email)\n\n";

    public static final String ACCOUNT_REGISTRATION_SUBJECT = "(Rush-CORP) Welcome  to Rush-CORP !!!";
    public static final String UPDATED_PHONE_NUMBER_SUBJECT = "(Rush-CORP) Contact no. updated Successfully !!!";
    public static final String BASE_LOCATION_CHANGE_REQUEST_SUBJECT = "(Rush-CORP) Base location change request";
    public static final String UPDATED_PASSWORD_SUBJECT = "(Rush-CORP) Your Password updated Successfully !!!";
    public static final String UPDATED_USERNAME_SUBJECT = "(Rush-CORP) Your username updated Successfully !!!";
    public static final String EMAIL_UPDATING_SUBJECT = "(Rush-CORP) Email re-registered Successfully !!!";
    public static final String BASE_LOCATION_FAILED_RESPONSE_SUBJECT = "(Rush-CORP) Base location change request denied";
    public static final String BASE_LOCATION_SUCCESSFUL_RESPONSE_SUBJECT = "(Rush-CORP) Base location change request Successfully !!!";
    public static final String AUTHORITY_RAISE_REQUEST_SUBJECT = "(Rush-CORP) Authority promotion change request";
    public static final String MASTER_USER_REGISTRATION_REQUEST_SUBJECT = "(Rush-CORP) Account registration request";
    public static final String MASTER_USER_REGISTRATION_SUCCESSFUL_RESPONSE_SUBJECT = "(Rush-CORP) Welcome  to Rush-CORP !!!";
    public static final String MASTER_USER_REGISTRATION_FAILED_RESPONSE_SUBJECT = "(Rush-CORP) Account registration request denied";
    public static final String AUTHORITY_RAISE_REQUEST_SUCCESSFUL_SUBJECT = "(Rush-CORP) Congratulation on your authority promotion!!!";
    public static final String AUTHORITY_RAISE_REQUEST_DENIED_SUBJECT = "(Rush-CORP) Authority promotion is denied";
    public static final String DEGRADE_AUTHORITY_SUBJECT = "(Rush-CORP) Authority demoted.";
    public static final String EMAIL_CLOSE_USER_ACCOUNT_SUBJECT = "(Rush-CORP) Account closing request";
    public static final String EMAIL_ACCOUNT_CLOSED_SUCCESSFUL_SUBJECT = "(Rush-CORP) Account closed successfully";
    public static final String EMAIL_ACCOUNT_CLOSED_REJECTED_SUBJECT = "(Rush-CORP) Account closing request rejected";
    public static final String EMAIL_OTP_MESSAGE_SUBJECT = "(Rush-CORP) Email verification code";

    public static final String EMAIL_USERID = "\n\nYour userId: ";
    public static final String EMAIL_USERNAME = "\n\nYour userName: ";
    public static final String EMAIL_CONTACT = "\n\nYour Contact: ";
    public static final String EMAIL_UNIQUE_IDENTIFICATION_NUMBER = "\n\nYour UIN:";

    public static final String EMAIL_UPDATED = "\n\nYour Email has been updated to current Email address.";
    public static final String EMAIL_UPDATED_PASSWORD = "\n\nYour updated Password: ";
    public static final String EMAIL_UPDATED_USERNAME = "Your username has been updated to: ";
    public static final String EMAIL_UPDATED_PHONE_NUMBER = "Your Contact number has been updated to: ";
    public static final String BASE_LOCATION_REQUEST_REGISTERED = "Your request to change your base location has been registered.\nBase location requested: ";
    public static final String BASE_LOCATION_FAILED_RESPONSE = "Your response request to change the Base location is denied.\nYour base location is :";
    public static final String BASE_LOCATION_SUCCESSFUL_RESPONSE = "Your response request to change the Base location is Approved.\nYour base location changed to:";
    public static final String AUTHORITY_RAISE_REQUEST_REGISTERED = "Your request of promotion of your authority has been registered.";
    public static final String MASTER_USER_REGISTRATION_REQUEST_REGISTERED = "Your request for account registration has been raised.";
    public static final String MASTER_USER_REGISTRATION_SUCCESSFUL_RESPONSE = "Your account registration has been accepted.";
    public static final String MASTER_USER_REGISTRATION_FAILED_RESPONSE = "Your account registration has been denied.\nYou can still register from the user section.";
    public static final String AUTHORITY_RAISE_REQUEST_SUCCESSFUL = "Congratulation!!! Your authority is promoted to MASTER-USER.";
    public static final String AUTHORITY_RAISE_REQUEST_DENIED = "Your request for authority raise is denied.";
    public static final String EMAIL_DEGRADED_AUTHORITY = "Your authority has been demoted to Client as per your performance.";
    public static final String EMAIL_CLOSE_USER_ACCOUNT_REQUEST = "Your request for closing your account is submitted.";
    public static final String EMAIL_ACCOUNT_CLOSED_SUCCESSFULLY = "Your Account has been closed successfully.";
    public static final String EMAIL_ACCOUNT_CLOSED_REJECTED = "Your Account has not been closed due some management conflicts. Please contact administration for the same.";
    public static final String EMAIL_OTP_MESSAGE = "Your otp (one time password) is ";
    public static final String EMAIL_OTP_WARNING = "\n\n(please do not share it with anyone)";

    public static final String EMAIL_END_WARNING = "\n\n\n\n\n\nThanks and Regards,\nRush-CORP Inc.\n\n\n\n(please do not share your credentials to any one claiming authority.)";
}

