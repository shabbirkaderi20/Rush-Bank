package com.rush.banking.userservice.responsevo;

import com.rush.banking.userservice.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVo {

    private UserResponseDto user;
    private String responseStatus;
}

