package com.rush.banking.userservice.procedure;

import com.rush.banking.userservice.entity.CloseUserRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CloseUserProcedure extends JpaRepository<CloseUserRequests, Long> {

    Optional<CloseUserRequests> findByUserId(Long userId);
    Optional<CloseUserRequests> findByPhoneNumber(Long phoneNumber);
}
