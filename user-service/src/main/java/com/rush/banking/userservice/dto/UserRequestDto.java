package com.rush.banking.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {

    @Column(unique = true)
    @Min(3)
    @Max(15)
    private String userName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;
    
    @Min(3)
    @Max(15)
    private String firstName;
    private String lastName;

    @Email
    @Column(unique = true)
    private String emailId;

    @Min(10)
    @Max(10)
    @Column(unique = true)
    private Long phoneNumber;
    @Min(3)
    private String baseLocation;

    private Long authorityId;
}
