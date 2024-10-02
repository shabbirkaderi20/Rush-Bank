package com.rush.banking.userservice.service;

import com.rush.banking.userservice.dto.CloseUserRequestsResponseDto;
import com.rush.banking.userservice.entity.CloseUserRequests;
import com.rush.banking.userservice.responsevo.CloseUserRequestsResponseVo;

import java.util.Optional;


public interface CloseAccountServiceFacade {

    String closeUserRequest(Long userId);
    String closeUserAccountResponse(Long userId, Long requestId, Boolean responseStatus);
    CloseUserRequestsResponseVo getClosedAccounts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Long userId);
    CloseUserRequestsResponseDto getClosedAccount(Long userId, Long requestId);
}
