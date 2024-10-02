package com.rush.banking.userservice.procedure;

import com.rush.banking.userservice.entity.DemotedAuthorityProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemotedAuthorityProcedure extends JpaRepository<DemotedAuthorityProcess, Long> {
}
