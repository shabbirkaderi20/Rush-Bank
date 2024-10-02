package com.rush.banking.userservice.controller;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.dto.OtpRequests;
import com.rush.banking.userservice.dto.PhoneNumberOtpRequestDto;
import com.rush.banking.userservice.dto.UserRequestDto;
import com.rush.banking.userservice.dto.UserResponseDto;
import com.rush.banking.userservice.responsevo.UserListResponseVo;
import com.rush.banking.userservice.service.UserServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceFacade userServiceFacade;

    @PostMapping(value= "/register-user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto) {

        return userServiceFacade.registerUser(userRequestDto);
    }

    @PatchMapping(value= "/update-username/{user-id}", consumes = "application/json", produces = "application/json")
    public UserResponseDto updateUserName(@PathVariable("user-id") Long userId, @RequestBody Map<String, Object> userNameUpdateRequestDto) {

        return userServiceFacade.updateUserName(userId, userNameUpdateRequestDto);
    }

    @PutMapping(value= "/update-contact")
    public String updateContact(@RequestParam("user-id") Long userId,
                                @RequestParam("phone-number") Long phoneNumber) {

        return userServiceFacade.updateContact(userId, phoneNumber);
    }

    @PostMapping(value= "/update-base-location")
    public String updateBaseLocation(@RequestParam("user-id") Long userId,
                                     @RequestParam("requested-base-location") String requestedBaseLocation) {

        return userServiceFacade.updateBaseLocation(userId, requestedBaseLocation);
    }

    @PostMapping(value= "/authority-request")
    public String registerAuthorityRequest(@RequestParam("user-id") Long userId) {

        return userServiceFacade.updateAuthority(userId);
    }

    @PutMapping(value= "/update-email")
    public String updateEmail(@RequestParam("email-id") String emailId,
                              @RequestParam("user-id") Long userId) {

        return userServiceFacade.updateEmail(emailId, userId);
    }

    @PutMapping(value= "/change-password")
    public String changePassword(@RequestParam("password") String password,
                                 @RequestParam("user-id") Long userId) {

        return userServiceFacade.changePassword(password, userId);
    }

    @GetMapping(value= "/get-user-list/{user-id}")
    public UserListResponseVo getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = Constants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = Constants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = Constants.SORT_DIR,required = false) String sortDir,
            @PathVariable("user-id") Long userId) {

        logger.info("Getting all user accounts details");
        return userServiceFacade.getAllUsers(pageNumber, pageSize, sortBy, sortDir, userId);
    }

    @GetMapping("/account-details/{user-id}")
    public UserResponseDto getUserDetails(@PathVariable("user-id") Long userId) {
        return userServiceFacade.getUserDetails(userId);
    }

    @PutMapping(value = "/close-user-request/{user-id}")
    public String closeUserRequest(@PathVariable("user-id") Long userId) {

        return userServiceFacade.closeUserRequest(userId);
    }

    @GetMapping(value= "/get-email-password/{email-id}")
    public String emailVerification(@PathVariable("email-id") String emailId) {

        return userServiceFacade.emailVerification(emailId);
    }

    @GetMapping(value= "/get-sms-password/{phone-number}")
    public String phoneVerification(@PathVariable("phone-number") Long phoneNumber) {

        return userServiceFacade.phoneVerification(phoneNumber);
    }

    @PostMapping(value = "/validate-otp")
    public Boolean otpVerification(@RequestBody OtpRequests otpRequests){

        return userServiceFacade.verifyOtpForEmail(otpRequests);
    }

    @PostMapping(value = "/validate-sms-otp")
    public Boolean contactOtpVerification(@RequestBody PhoneNumberOtpRequestDto phoneNumberOtpRequestDto){

        return userServiceFacade.verifyOtpForPhoneNumber(phoneNumberOtpRequestDto);
    }
}
