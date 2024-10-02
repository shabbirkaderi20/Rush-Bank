package com.rush.banking.userservice.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "authorities")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @Column(name = "user_name")
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "contact_num")
    private Long phoneNumber;
    private String baseLocation;
    private String uniqueIdentificationNumber;
    private Long authorityId;


}
