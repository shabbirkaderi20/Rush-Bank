package com.rush.banking.userservice.procedure;

import com.rush.banking.userservice.entity.BaseLocationRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseLocationProcedure extends JpaRepository<BaseLocationRequests, Long> {


}
