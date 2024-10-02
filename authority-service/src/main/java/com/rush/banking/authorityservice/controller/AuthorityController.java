package com.rush.banking.authorityservice.controller;

import com.rush.banking.authorityservice.service.AuthorityServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@EnableAspectJAutoProxy(proxyTargetClass = true)
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthorityServiceFacade authorityServiceFacade;

    @GetMapping("/all")
    public Set<String> getAllAuthorities() {
        return authorityServiceFacade.getAllAuthorities();
    }

    @GetMapping("")
    public String getAuthorityById(@RequestParam("id") Long authorityId) {
        return authorityServiceFacade.getAuthorityById(authorityId);
    }

}
