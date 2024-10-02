package com.rush.banking.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "base_location_requests")
public class BaseLocationRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long requestId;
    private Long userId;
    private String uniqueIdentificationNumber;
    private String previousBaseLocation;
    private String requestedBaseLocation;
    private Boolean solutionStatus;
    private Boolean responseStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRaised;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSolved;
}
