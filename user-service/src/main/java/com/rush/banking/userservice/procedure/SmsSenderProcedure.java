package com.rush.banking.userservice.procedure;

import com.rush.banking.userservice.entity.PhoneNumberOtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsSenderProcedure extends JpaRepository<PhoneNumberOtpRequest, Long> {

    Optional<PhoneNumberOtpRequest> findByPhoneNumber(Long phoneNumber);

    void deleteByPhoneNumber(Long phoneNumber);
}
