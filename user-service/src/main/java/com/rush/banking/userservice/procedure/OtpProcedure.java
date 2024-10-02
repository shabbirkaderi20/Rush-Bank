package com.rush.banking.userservice.procedure;

import com.rush.banking.userservice.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpProcedure extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmailId(String emailId);
}
