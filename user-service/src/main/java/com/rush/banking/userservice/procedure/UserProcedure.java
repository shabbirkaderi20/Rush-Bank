package com.rush.banking.userservice.procedure;

import com.rush.banking.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserProcedure extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmailId(String emailId);
    Optional<User> findByPhoneNumber(Long phoneNumber);
}
