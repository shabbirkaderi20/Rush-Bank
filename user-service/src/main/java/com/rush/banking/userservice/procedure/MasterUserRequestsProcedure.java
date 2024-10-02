package com.rush.banking.userservice.procedure;

import com.rush.banking.userservice.entity.MasterUserRequests;
import com.rush.banking.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterUserRequestsProcedure extends JpaRepository<MasterUserRequests, Long> {

    Optional<MasterUserRequests> findByUserName(String userName);
    Optional<MasterUserRequests> findByEmailId(String emailId);
    Optional<MasterUserRequests> findByPhoneNumber(Long phoneNumber);
}
