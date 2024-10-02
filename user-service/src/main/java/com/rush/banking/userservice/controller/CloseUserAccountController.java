package com.rush.banking.userservice.controller;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.dto.CloseUserRequestsResponseDto;
import com.rush.banking.userservice.responsevo.CloseUserRequestsResponseVo;
import com.rush.banking.userservice.service.CloseAccountServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/close-account")
public class CloseUserAccountController {

    @Autowired
    private CloseAccountServiceFacade closeAccountServiceFacade;

    @DeleteMapping(value = "/response/{user-id}/{request-id}/{response-status}")
    public String closeAccountResponse (@PathVariable("user-id") Long userId, @PathVariable("request-id") Long requestId,
                                        @PathVariable("response-status") Boolean responseStatus) {

        return closeAccountServiceFacade.closeUserAccountResponse(userId, requestId, responseStatus);
    }

    @GetMapping(value= "/get-closed-accounts/{user-id}")
    public CloseUserRequestsResponseVo getClosedAccounts(@RequestParam(value = "pageNumber",defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize",defaultValue = Constants.PAGE_SIZE,required = false) Integer pageSize,
                                                         @RequestParam(value="sortBy",defaultValue = Constants.SORT_BY,required = false) String sortBy,
                                                         @RequestParam(value = "sortDir",defaultValue = Constants.SORT_DIR,required = false) String sortDir,
                                                         @PathVariable("user-id") Long userId) {

        return closeAccountServiceFacade.getClosedAccounts(pageNumber, pageSize, sortBy, sortDir, userId);
    }

    @GetMapping(value= "/get-closed-account/{user-id}/{request-id}")
    public CloseUserRequestsResponseDto getClosedAccount(@PathVariable("user-id") Long userId,
                                                         @PathVariable("request-id") Long requestId) {

        return closeAccountServiceFacade.getClosedAccount(userId, requestId);
    }
}
