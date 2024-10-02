package com.rush.banking.userservice.service;

import com.rush.banking.userservice.responsevo.AuthorityManagementResponseVo;
import org.springframework.http.ResponseEntity;

public interface AuthorityManagementServiceFacade {

    String requestUserAuthorityRequest(Long userId);
    ResponseEntity<?> processUpraiseAuthorityRequest(Long userId, Long requestId, Boolean responseStatus);
    String degradeAuthority(Long userId, Long clientId);
    AuthorityManagementResponseVo getRequestList(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Long userId);
}
