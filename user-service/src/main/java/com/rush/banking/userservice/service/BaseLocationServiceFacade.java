package com.rush.banking.userservice.service;

import com.rush.banking.userservice.responsevo.BaseLocationRequestsResponseVo;
import com.rush.banking.userservice.responsevo.ResponseTemplateVo;

public interface BaseLocationServiceFacade {

    String baseLocationChangeRequest(Long userId, String emailId, String uniqueIdentificationNumber, String previousBaseLocation, String requestedBaseLocation);
    ResponseTemplateVo baseLocationResponse(Long userId, Long requestId, Boolean responseStatus);
    BaseLocationRequestsResponseVo getRequestList(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Long userId);
}
