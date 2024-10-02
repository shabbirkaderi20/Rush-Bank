package com.rush.banking.authorityservice.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface AuthorityServiceFacade {


    Set<String> getAllAuthorities();
    String getAuthorityById(Long authorityId);
}
