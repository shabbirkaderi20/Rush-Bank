package com.rush.banking.userservice.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "master_user_requests")
public class MasterUserRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long requestId;

    @Column(name = "user_name", unique = true)
    @Min(3)
    private String userName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    @Min(8)
    private String password;

    @Min(3)
    private String firstName;
    private String lastName;

    @Email
    @Column(name = "email_id", unique = true)
    private String emailId;

//    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$")
    @Max(10)
    @Min(10)
    @Column(name = "contact_num", unique = true)
    private Long phoneNumber;

    @Min(3)
    @Enumerated(EnumType.STRING)
    private String baseLocation;

    private Boolean solutionStatus;
    private Boolean responseStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRaised;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSolved;
}
