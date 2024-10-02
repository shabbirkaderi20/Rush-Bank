package com.rush.banking.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "otp_phone_num_mapper")
public class PhoneNumberOtpRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long entryId;

    private Long phoneNumber;
    private String carrierKey;
    private String carrierObject;
    private String callerNameKey;
    private String callerNameObject;
    private String countryCode;
    private String nationalFormat;
    private String otp;
}
