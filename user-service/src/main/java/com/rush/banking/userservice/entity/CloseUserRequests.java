package com.rush.banking.userservice.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "closed_account_list")
public class CloseUserRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long closeRequestId;

    @Column(unique=true)
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
