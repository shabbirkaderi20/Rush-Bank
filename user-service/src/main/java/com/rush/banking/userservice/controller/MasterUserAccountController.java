package com.rush.banking.userservice.controller;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.dto.MasterUserResponseDto;
import com.rush.banking.userservice.responsevo.MasterUserRequestsResponseVo;
import com.rush.banking.userservice.service.MasterUserRequestsServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/master-user")
public class MasterUserAccountController {

    @Autowired
    private MasterUserRequestsServiceFacade masterUserRequestsServiceFacade;

    @PostMapping(value= "/account-request")
    public ResponseEntity<?> promoteAuthority(@RequestParam("user-id") Long userId,
                                              @RequestParam("request-id") Long requestId,
                                              @RequestParam("response-status") Boolean responseStatus) {

        return masterUserRequestsServiceFacade.promoteAuthority(userId, requestId, responseStatus);
    }

    @GetMapping(path = "/account-request/{user-id}/{request-id}")
    public MasterUserResponseDto getMasterAccountRequest(@PathVariable("user-id") Long userId,
                                                         @PathVariable("request-id") Long requestId) {

        return masterUserRequestsServiceFacade.getMasterAccountRequest(userId, requestId);
    }

    @GetMapping(path = "/account-request-list/{user-id}")
    public MasterUserRequestsResponseVo getMasterAccountRequestList(@PathVariable("user-id") Long userId,
                                                                    @RequestParam(value = "pageNumber",defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                    @RequestParam(value = "pageSize",defaultValue = Constants.PAGE_SIZE,required = false) Integer pageSize,
                                                                    @RequestParam(value="sortBy",defaultValue = Constants.SORT_BY,required = false) String sortBy,
                                                                    @RequestParam(value = "sortDir",defaultValue = Constants.SORT_DIR,required = false) String sortDir) {

        return masterUserRequestsServiceFacade.getMasterAccountRequestList(pageNumber, pageSize, sortBy, sortDir, userId);
    }
}
