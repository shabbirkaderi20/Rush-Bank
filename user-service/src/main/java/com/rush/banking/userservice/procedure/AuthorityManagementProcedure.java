package com.rush.banking.userservice.procedure;

import com.rush.banking.userservice.entity.AuthorityManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityManagementProcedure extends JpaRepository<AuthorityManagement, Long> {

}
