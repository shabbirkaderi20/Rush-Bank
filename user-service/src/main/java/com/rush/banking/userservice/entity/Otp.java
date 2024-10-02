package com.rush.banking.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "otp_mapper")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long entryId;

    private String emailId;
    private String otp;

}
