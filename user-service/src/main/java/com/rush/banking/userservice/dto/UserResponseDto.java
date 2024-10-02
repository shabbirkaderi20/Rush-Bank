package com.rush.banking.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {

    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailId;
    private Long phoneNumber;
    private String baseLocation;
    private String uniqueIdentificationNumber;
    private String authority;
}
