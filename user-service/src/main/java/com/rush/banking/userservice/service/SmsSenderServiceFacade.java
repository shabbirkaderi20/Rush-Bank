package com.rush.banking.userservice.service;

import com.rush.banking.userservice.entity.PhoneNumberOtpRequest;
import com.rush.banking.userservice.exceptions.PhoneNumberNotVerifiedException;

public interface SmsSenderServiceFacade {

    PhoneNumberOtpRequest sendSms(Long phoneNumber, String otp) throws PhoneNumberNotVerifiedException;
}
