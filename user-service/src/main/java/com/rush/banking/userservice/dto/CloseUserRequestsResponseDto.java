package com.rush.banking.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CloseUserRequestsResponseDto {

    private Long closeRequestId;
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailId;
    private Long phoneNumber;
    private String baseLocation;
    private String uniqueIdentificationNumber;
    private Long authorityId;
    private Date requestDate;
    private Date responseDate;
    private Boolean isSolved;
    private Boolean isApproved;
}
