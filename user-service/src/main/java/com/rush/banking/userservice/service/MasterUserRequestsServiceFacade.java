package com.rush.banking.userservice.service;

import com.rush.banking.userservice.dto.MasterUserResponseDto;
import com.rush.banking.userservice.entity.User;
import com.rush.banking.userservice.responsevo.MasterUserRequestsResponseVo;
import org.springframework.http.ResponseEntity;

public interface MasterUserRequestsServiceFacade {

    String registerMasterUser(User user);
    ResponseEntity<?> promoteAuthority(Long userId, Long requestId, Boolean responseStatus);
    MasterUserResponseDto getMasterAccountRequest(Long userId, Long requestId);
    MasterUserRequestsResponseVo getMasterAccountRequestList(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Long userId);
}
