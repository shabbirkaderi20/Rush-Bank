package com.rush.banking.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorityManagementRequestDto {

    private Long userId;
    private String requestedAuthority;
}
