package com.rush.banking.userservice.service;

import com.rush.banking.userservice.dto.OtpRequests;
import com.rush.banking.userservice.dto.PhoneNumberOtpRequestDto;
import com.rush.banking.userservice.dto.UserRequestDto;
import com.rush.banking.userservice.dto.UserResponseDto;
import com.rush.banking.userservice.entity.User;
import com.rush.banking.userservice.responsevo.UserListResponseVo;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public interface UserServiceFacade {

    ResponseEntity<?> registerUser(UserRequestDto userRequestDto);
    String updateEmail(String emailId, Long userId);
    String changePassword(String password, Long userId);
    UserResponseDto updateUserName(Long userId, Map<String, Object> userNameUpdateRequestDto);
    String updateContact(Long userId, Long phoneNumber);
    String updateBaseLocation(Long userId, String requestedBaseLocation);
    String updateAuthority(Long userId);
    User checkUser(Long userId);
    UserListResponseVo getAllUsers(Integer pageNumber, Integer pageSize,
                                   String sortBy, String sortDir, Long userId);
    String closeUserRequest(Long userId);
    UserResponseDto getUserDetails(Long userId);
    UserResponseDto getUserDetailsForAuthentication(String userName);
    String emailVerification(String emailId);
    Boolean verifyOtpForEmail(OtpRequests otpRequests);
    String phoneVerification(Long phoneNumber);
    Boolean verifyOtpForPhoneNumber(PhoneNumberOtpRequestDto phoneNumberOtpRequestDto);
}
