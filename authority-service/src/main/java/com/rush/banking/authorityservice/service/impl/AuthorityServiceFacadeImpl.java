package com.rush.banking.authorityservice.service.impl;

import com.rush.banking.authorityservice.entity.Authorities;
import com.rush.banking.authorityservice.exception.NoAuthorityFoundException;
import com.rush.banking.authorityservice.procedure.AuthorityProcedure;
import com.rush.banking.authorityservice.service.AuthorityServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("AuthorityServiceFacade")
public class AuthorityServiceFacadeImpl implements AuthorityServiceFacade {

    @Autowired
    private AuthorityProcedure authorityProcedure;

    @Override
    public Set<String> getAllAuthorities() {

        return authorityProcedure.findAll().stream()
                .map(authority-> authority.getAuthority().toUpperCase(Locale.ROOT)).collect(Collectors.toSet());
    }

    @Override
    public String getAuthorityById(Long authorityId) {

        Optional<Authorities> authority= authorityProcedure.findById(authorityId);

        if (authority.isPresent()) {
            return authority.get().getAuthority().toUpperCase(Locale.ROOT);
        }
        throw new NoAuthorityFoundException();
    }
}
