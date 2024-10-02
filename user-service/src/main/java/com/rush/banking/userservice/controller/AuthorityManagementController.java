package com.rush.banking.userservice.controller;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.responsevo.AuthorityManagementResponseVo;
import com.rush.banking.userservice.service.AuthorityManagementServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/authorities")
public class AuthorityManagementController {

    @Autowired
    private AuthorityManagementServiceFacade authorityManagementServiceFacade;

    @PostMapping(value= "/upraise-authority")
    public ResponseEntity<?> promoteAuthority(@RequestParam("user-id") Long userId,
                                              @RequestParam("request-id") Long requestId,
                                              @RequestParam("response-status") Boolean responseStatus) {

        return authorityManagementServiceFacade.processUpraiseAuthorityRequest(userId, requestId, responseStatus);
    }

    @PostMapping(value= "/degrade-authority")
    public String degradeAuthority(@RequestParam("user-id") Long userId,
                                   @RequestParam("client-id") Long clientId) {

        return authorityManagementServiceFacade.degradeAuthority(userId, clientId);
    }

    @GetMapping("/authority-management/{user-id}")
    public AuthorityManagementResponseVo getRequestList(@RequestParam(value = "pageNumber",defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize",defaultValue = Constants.PAGE_SIZE,required = false) Integer pageSize,
                                                        @RequestParam(value="sortBy",defaultValue = Constants.SORT_BY,required = false) String sortBy,
                                                        @RequestParam(value = "sortDir",defaultValue = Constants.SORT_DIR,required = false) String sortDir,
                                                        @PathVariable("user-id") Long userId) {

        return authorityManagementServiceFacade.getRequestList(pageNumber, pageSize, sortBy, sortDir, userId);
    }

}
