package com.rush.banking.authorityservice.procedure;

import com.rush.banking.authorityservice.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityProcedure extends JpaRepository<Authorities, Long> {


}
