package com.rush.banking.userservice.service;

import com.rush.banking.userservice.entity.LoginRequest;

public interface LoginServiceFacade  {

    String loginUser(LoginRequest loginRequest);

}
