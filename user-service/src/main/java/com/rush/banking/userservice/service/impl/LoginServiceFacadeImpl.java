package com.rush.banking.userservice.service.impl;

import com.rush.banking.userservice.entity.LoginRequest;
import com.rush.banking.userservice.entity.User;
import com.rush.banking.userservice.exceptions.PasswordIncorrectException;
import com.rush.banking.userservice.exceptions.UserNotFoundException;
import com.rush.banking.userservice.procedure.UserProcedure;
import com.rush.banking.userservice.service.LoginServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("LoginServiceFacade")
public class LoginServiceFacadeImpl implements LoginServiceFacade {

    @Autowired
    private UserProcedure userProcedure;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String loginUser(LoginRequest loginRequest) {

        Optional<User> user= userProcedure.findByUserName(loginRequest.getUserName());

        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }else if(!passwordEncoder.encode(loginRequest.getPassword())
                .equals(user.get().getPassword())) {
            throw new PasswordIncorrectException();
        }

        return "Login successful";
    }
}