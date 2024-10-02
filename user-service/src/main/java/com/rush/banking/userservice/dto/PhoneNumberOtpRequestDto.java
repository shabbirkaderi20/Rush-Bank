package com.rush.banking.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberOtpRequestDto {

    private Long phoneNumber;
    private String otp;
}
