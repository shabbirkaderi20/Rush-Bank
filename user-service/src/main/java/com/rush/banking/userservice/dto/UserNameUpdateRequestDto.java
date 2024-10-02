package com.rush.banking.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserNameUpdateRequestDto {

    @Min(3)
    @Max(15)
    private String firstName;
    private String lastName;
}
