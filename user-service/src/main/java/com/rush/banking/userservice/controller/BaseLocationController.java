package com.rush.banking.userservice.controller;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.responsevo.BaseLocationRequestsResponseVo;
import com.rush.banking.userservice.responsevo.ResponseTemplateVo;
import com.rush.banking.userservice.service.BaseLocationServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/locators")
public class BaseLocationController {

    @Autowired
    private BaseLocationServiceFacade baseLocationServiceFacade;

    @PostMapping(value= "/base-location-response")
    public ResponseTemplateVo updateBaseLocationResponse(@RequestParam("user-id") Long userId,
                                                         @RequestParam("request-id") Long requestId,
                                                         @RequestParam("response") Boolean responseStatus) {

        return baseLocationServiceFacade.baseLocationResponse(userId, requestId, responseStatus);
    }

    @GetMapping("/base-location-request=list/{user-id}")
    public BaseLocationRequestsResponseVo getRequestList(@RequestParam(value = "pageNumber",defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize",defaultValue = Constants.PAGE_SIZE,required = false) Integer pageSize,
                                                         @RequestParam(value="sortBy",defaultValue = Constants.SORT_BY,required = false) String sortBy,
                                                         @RequestParam(value = "sortDir",defaultValue = Constants.SORT_DIR,required = false) String sortDir,
                                                         @PathVariable("user-id") Long userId) {

        return baseLocationServiceFacade.getRequestList(pageNumber, pageSize, sortBy, sortDir, userId);
    }
}
