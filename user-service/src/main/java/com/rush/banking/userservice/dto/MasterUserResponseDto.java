package com.rush.banking.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterUserResponseDto {

    private Long requestId;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailId;
    private Long phoneNumber;
    private String baseLocation;
    private Boolean solutionStatus;
    private Boolean responseStatus;
    private Date dateRaised;
    private Date dateSolved;
}
