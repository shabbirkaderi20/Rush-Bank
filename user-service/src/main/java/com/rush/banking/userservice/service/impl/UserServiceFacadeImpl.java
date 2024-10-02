package com.rush.banking.userservice.service.impl;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.dto.OtpRequests;
import com.rush.banking.userservice.dto.PhoneNumberOtpRequestDto;
import com.rush.banking.userservice.entity.Otp;
import com.rush.banking.userservice.dto.UserRequestDto;
import com.rush.banking.userservice.dto.UserResponseDto;
import com.rush.banking.userservice.entity.PhoneNumberOtpRequest;
import com.rush.banking.userservice.entity.User;
import com.rush.banking.userservice.exceptions.*;
import com.rush.banking.userservice.procedure.OtpProcedure;
import com.rush.banking.userservice.procedure.SmsSenderProcedure;
import com.rush.banking.userservice.procedure.UserProcedure;
import com.rush.banking.userservice.responsevo.UserListResponseVo;
import com.rush.banking.userservice.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("UserServiceFacade")
public class UserServiceFacadeImpl implements UserServiceFacade {

    @Autowired
    private UserProcedure userProcedure;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSenderServiceFacade mailSenderServiceFacade;

    @Autowired
    private BaseLocationServiceFacade baseLocationServiceFacade;

    @Autowired
    private AuthorityManagementServiceFacade authorityManagementServiceFacade;

    @Autowired
    private MasterUserRequestsServiceFacade masterUserRequestsServiceFacade;

    @Autowired
    private CloseAccountServiceFacade closeAccountServiceFacade;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OtpProcedure otpProcedure;

    @Autowired
    private SmsSenderServiceFacade smsSenderServiceFacade;

    @Autowired
    private SmsSenderProcedure smsSenderProcedure;

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public ResponseEntity<?> registerUser(UserRequestDto userRequestDto) {

        User user= modelMapper.map(userRequestDto, User.class);
            user.setPassword(encodePassword(user.getPassword()));
            user.setUniqueIdentificationNumber(generateUniqueIdentificationNumber(user));

        Optional<User> userNameValidator = userProcedure.findByUserName(user.getUserName());

        if (userNameValidator.isPresent()) {

            throw new UserNameAlreadyExistsException();
        } else if(user.getAuthorityId()== 2L) {

            return new ResponseEntity<>(masterUserRequestsServiceFacade.registerMasterUser(user), HttpStatus.OK);
        }else {
            User userReturned = userProcedure.save(user);
            mailSenderServiceFacade.sendMail(userReturned.getEmailId(),
                    Constants.EMAIL_START_WARNING + Constants.EMAIL_USERID +
                            userReturned.getUserId() + Constants.EMAIL_USERNAME +
                            userReturned.getUserName() +
                            Constants.EMAIL_UNIQUE_IDENTIFICATION_NUMBER +
                            user.getUniqueIdentificationNumber() + Constants.EMAIL_CONTACT +
                            userReturned.getPhoneNumber() + Constants.EMAIL_END_WARNING,
                    Constants.ACCOUNT_REGISTRATION_SUBJECT);

            UserResponseDto userResponse=  modelMapper.map(userReturned, UserResponseDto.class);
                userResponse.setAuthority(getAuthority(user.getAuthorityId()));

            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
    }

    @Override
    public String updateEmail(String emailId, Long userId) {

        User user= checkUser(userId);

        Optional<User> userEmail = userProcedure.findByEmailId(emailId);

        if(user.getEmailId().equals(emailId)) {
            throw new EmailCannotBeSameException();
        }else if(userEmail.isPresent()) {
            throw new EmailAlreadyTakenException();
        } else{
                user.setEmailId(emailId);
                User userReturned= userProcedure.save(user);

            mailSenderServiceFacade.sendMail(userReturned.getEmailId(),
                Constants.EMAIL_START_WARNING + Constants.EMAIL_UPDATED +
                    Constants.EMAIL_END_WARNING, Constants.EMAIL_UPDATING_SUBJECT);

            return Constants.UPDATE_EMAIL_STRING+ userReturned.getEmailId() ;
        }
    }

    @Override
    public String changePassword(String password, Long userId) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user= checkUser(userId);

        if(bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new PasswordCannotBeSameException();
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(password));

            User userWithUpdatedPassword = userProcedure.save(user);
            mailSenderServiceFacade.sendMail(userWithUpdatedPassword.getEmailId(),
                    Constants.EMAIL_START_WARNING + Constants.EMAIL_UPDATED_PASSWORD +
                            password + Constants.EMAIL_END_WARNING
                    , Constants.UPDATED_PASSWORD_SUBJECT);

            return Constants.UPDATE_PASSWORD_STRING + password;
        }
    }

    @Override
    public UserResponseDto updateUserName(Long userId, Map<String, Object> userNameUpdateRequestDto) {

        User user= checkUser(userId);

        userNameUpdateRequestDto.forEach((key, value)-> {
            Optional<Field> values= Optional.ofNullable(ReflectionUtils.findField(User.class, key));
            values.ifPresent(field -> {
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            });
        });


        UserResponseDto userResponseDto= modelMapper.map(userProcedure.save(user), UserResponseDto.class);

        mailSenderServiceFacade.sendMail(userResponseDto.getEmailId(),
                Constants.EMAIL_START_WARNING + Constants.EMAIL_UPDATED_USERNAME +
                    userResponseDto.getFirstName() + " " + userResponseDto.getLastName() +
                    Constants.EMAIL_END_WARNING, Constants.UPDATED_USERNAME_SUBJECT);

        return userResponseDto;

    }

    @Override
    public String updateContact(Long userId, Long phoneNumber) {

        User user= checkUser(userId);

        if(user.getPhoneNumber().equals(phoneNumber)) {
            throw new OldContactRepeatedException();
        }

        Optional<User> userContact= userProcedure.findByPhoneNumber(phoneNumber);

        if(userContact.isPresent()) {
            throw new ContactAlreadyExistsException();
        } else {

            user.setPhoneNumber(phoneNumber);

            UserResponseDto userResponseDto= modelMapper.map(userProcedure.save(user), UserResponseDto.class);

            mailSenderServiceFacade.sendMail(userResponseDto.getEmailId(),
                    Constants.EMAIL_START_WARNING + Constants.EMAIL_UPDATED_PHONE_NUMBER +
                            userResponseDto.getPhoneNumber() + Constants.EMAIL_END_WARNING,
                    Constants.UPDATED_PHONE_NUMBER_SUBJECT);

            return Constants.UPDATE_PHONE_NUMBER_STRING+ userResponseDto.getPhoneNumber();
        }
    }

    @Override
    public String updateBaseLocation(Long userId, String requestedBaseLocation) {

        User user= checkUser(userId);

        String previousBaseLocation= user.getBaseLocation();
        String uniqueIdentificationNumber= user.getUniqueIdentificationNumber();
        String emailId= user.getEmailId();

        if(user.getBaseLocation().equals(requestedBaseLocation)) {
            throw new SameBaseLocationException();
        }

        return baseLocationServiceFacade.baseLocationChangeRequest(userId, emailId, uniqueIdentificationNumber, previousBaseLocation, requestedBaseLocation);

    }

    @Override
    public String updateAuthority(Long userId) {

        User user= checkUser(userId);

        if(user.getAuthorityId()!= 3L) {
            throw new UserRequestIsNotAuthorized();
        }else {

            return authorityManagementServiceFacade.requestUserAuthorityRequest(userId);
        }
    }

    @Override
    public User checkUser(Long userId) {

        Optional<User> user= userProcedure.findById(userId);

        if(user.isEmpty())
            throw new UserNotFoundException();

        return user.get();
    }

    @Override
    public UserListResponseVo getAllUsers(Integer pageNumber, Integer pageSize,
                                             String sortBy, String sortDir, Long userId) {

        Sort sort= null;

        if(sortDir.equalsIgnoreCase("ASC")) {
            sort= Sort.by(sortBy).ascending();
        }else {
            sort= Sort.by(sortBy).descending();
        }

        Optional<User> userReturned= userProcedure.findById(userId);

        if(userReturned.isEmpty() || userReturned.get().getUserId() != 1L) {
            throw new UserAuthorityForbiddenException();
        }
        //Pagination
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        Page<User> usersReturned= userProcedure.findAll(pageable);
        List<User> users= usersReturned.getContent();

        List<UserResponseDto> userList = new ArrayList<>();

        users.forEach(user -> {
            UserResponseDto userResponseDto= modelMapper.map(user, UserResponseDto.class);
            userResponseDto.setAuthority(getAuthority(user.getAuthorityId()));

            userList.add(userResponseDto);
        });

        UserListResponseVo userListResponseVo= new UserListResponseVo();
            userListResponseVo.setContent(userList);
            userListResponseVo.setPageNumber(usersReturned.getNumber());
            userListResponseVo.setPageSize(usersReturned.getSize());
            userListResponseVo.setTotalElements(usersReturned.getTotalElements());
            userListResponseVo.setTotalPages(usersReturned.getTotalPages());
            userListResponseVo.setLastPage(usersReturned.isLast());

        return userListResponseVo;
    }

    @Override
    public String closeUserRequest(Long userId) {

        return closeAccountServiceFacade.closeUserRequest(userId);
    }

    @Override
    public UserResponseDto getUserDetails(Long userId) {

        User user= checkUser(userId);

        UserResponseDto userResponseDto= modelMapper.map(user, UserResponseDto.class);
        userResponseDto.setAuthority(getAuthority(user.getAuthorityId()));

        return userResponseDto;
    }

    @Override
    public UserResponseDto getUserDetailsForAuthentication(String userName) {

        Optional<User> user= userProcedure.findByUserName(userName);

        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserResponseDto userResponseDto= modelMapper.map(user, UserResponseDto.class);
        userResponseDto.setAuthority(getAuthority(user.get().getAuthorityId()));

        return userResponseDto;
    }

    @Override
    public String emailVerification(String emailId) {

        Optional<User> emailValidator = userProcedure.findByEmailId(emailId);

        if (emailValidator.isPresent()) {
            throw new EmailAlreadyTakenException();
        }
        try {

            String otp= getOtpForVerification();

            mailSenderServiceFacade.sendMail(emailId,
                    Constants.EMAIL_START_WARNING+ Constants.EMAIL_OTP_MESSAGE+
                            otp+ Constants.EMAIL_OTP_WARNING+ Constants.EMAIL_END_WARNING,
                    Constants.EMAIL_OTP_MESSAGE_SUBJECT);

            Optional<Otp> otpReturned = otpProcedure.findByEmailId(emailId);

            if(otpReturned.isPresent()) {

                otpReturned.get().setOtp(otp);
                otpProcedure.saveAndFlush(otpReturned.get());
            } else {

                Otp otpProcured= new Otp();
                    otpProcured.setEmailId(emailId);
                    otpProcured.setOtp(otp);

                otpProcedure.saveAndFlush(otpProcured);
            }
            return otp;

        } catch(Exception exception) {
            throw new EmailNotVerifiedException();
        }
    }

    @Override
    public Boolean verifyOtpForEmail(OtpRequests otpRequests) {

        Optional<Otp> otp = otpProcedure.findByEmailId(otpRequests.getEmailId());

        if(otp.isEmpty()) {
            throw new EmailNotVerifiedException();
        } else return otp.get().getOtp().equals(otpRequests.getOtp());
    }

    @Override
    public String phoneVerification(Long phoneNumber) {

        Optional<User> contactValidator = userProcedure.findByPhoneNumber(phoneNumber);

        if (contactValidator.isPresent()) {

            throw new ContactAlreadyExistsException();
        }
        try {

            String otp= getOtpForVerification();

            PhoneNumberOtpRequest phoneNumberOtpRequest=
                    smsSenderServiceFacade.sendSms(phoneNumber, otp);

            Optional<PhoneNumberOtpRequest> otpReturned =
                    smsSenderProcedure.findByPhoneNumber(phoneNumber);

            if(otpReturned.isPresent()) {

                smsSenderProcedure.deleteByPhoneNumber(phoneNumber);
            }

            smsSenderProcedure.saveAndFlush(phoneNumberOtpRequest);

            return otp;

        } catch(Exception exception) {
            throw new PhoneNumberNotVerifiedException();
        }

    }

    @Override
    public Boolean verifyOtpForPhoneNumber(PhoneNumberOtpRequestDto phoneNumberOtpRequestDto) {

        Optional<PhoneNumberOtpRequest> otp = smsSenderProcedure.findByPhoneNumber(phoneNumberOtpRequestDto.getPhoneNumber());

        if(otp.isEmpty()) {
            throw new PhoneNumberNotVerifiedException();
        } else return otp.get().getOtp().equals(phoneNumberOtpRequestDto.getOtp());
    }

    public String
    generateUniqueIdentificationNumber(User user) {

        return "000"+ user.getAuthorityId()+
                user.getBaseLocation().substring(0, 3).toUpperCase(Locale.ROOT)+
                user.getPhoneNumber().toString().substring(7, 10)+
                user.getFirstName().substring(0, 4).toUpperCase(Locale.ROOT)+
                user.getLastName();
    }

    public String encodePassword(String password) {

        String regex= "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher= pattern.matcher(password);

        if (matcher.matches()) return passwordEncoder.encode(password);
        else throw new PasswordSyntaxIncorrectException();
    }

    private String getOtpForVerification() {

        Random rnd = new Random();
        return String.format("%06d", rnd.nextInt(999999));
    }

    private String getAuthority(Long authorityId) {

        return webClient.build().get()
                .uri("/authority?id=" + authorityId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(String.class)
                .block();
    }
}