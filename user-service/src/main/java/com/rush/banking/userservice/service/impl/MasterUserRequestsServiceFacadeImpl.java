package com.rush.banking.userservice.service.impl;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.dto.MasterUserResponseDto;
import com.rush.banking.userservice.dto.UserResponseDto;
import com.rush.banking.userservice.entity.MasterUserRequests;
import com.rush.banking.userservice.entity.User;
import com.rush.banking.userservice.exceptions.*;
import com.rush.banking.userservice.procedure.MasterUserRequestsProcedure;
import com.rush.banking.userservice.procedure.UserProcedure;
import com.rush.banking.userservice.responsevo.MasterUserRequestsResponseVo;
import com.rush.banking.userservice.responsevo.ResponseTemplateVo;
import com.rush.banking.userservice.service.MailSenderServiceFacade;
import com.rush.banking.userservice.service.MasterUserRequestsServiceFacade;
import jakarta.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("MasterUserRequestsServiceFacade")
public class MasterUserRequestsServiceFacadeImpl implements MasterUserRequestsServiceFacade {

    @Autowired
    private MasterUserRequestsProcedure masterUserRequestsProcedure;

    @Autowired
    private MailSenderServiceFacade mailSenderServiceFacade;

    @Autowired
    private UserProcedure userProcedure;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerMasterUser(User user) {

        MasterUserRequests masterUserRequests= new MasterUserRequests();
            masterUserRequests.setUserName(user.getUserName());
            masterUserRequests.setPassword(user.getPassword());
            masterUserRequests.setFirstName(user.getFirstName());
            masterUserRequests.setLastName(user.getLastName());
            masterUserRequests.setEmailId(user.getEmailId());
            masterUserRequests.setPhoneNumber(user.getPhoneNumber());
            masterUserRequests.setBaseLocation(user.getBaseLocation());
            masterUserRequests.setDateRaised(new Timestamp(System.currentTimeMillis()));
            masterUserRequests.setResponseStatus(false);

        try {
            masterUserRequestsProcedure.saveAndFlush(masterUserRequests);
        } catch(ConstraintViolationException ex) {
            StringBuilder errorMsg = new StringBuilder();
            ex.getConstraintViolations().forEach(violation -> {
                errorMsg.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            });

            return "Validation error: " + errorMsg.toString();
        }

        mailSenderServiceFacade.sendMail(user.getEmailId(),
                Constants.EMAIL_START_WARNING + Constants.MASTER_USER_REGISTRATION_REQUEST_REGISTERED +
                        Constants.EMAIL_END_WARNING,
                Constants.MASTER_USER_REGISTRATION_REQUEST_SUBJECT);

        return Constants.MASTER_USER_REGISTRATION_REQUEST_REGISTERED_STRING;
    }

    @Override
    public ResponseEntity<?> promoteAuthority(Long userId, Long requestId, Boolean responseStatus) {

        if(userId != 1L) {
            throw new UserAuthorityForbiddenException();
        }

        Optional<MasterUserRequests> masterUserRequests= masterUserRequestsProcedure.findById(requestId);

        if(masterUserRequests.isEmpty()) {
            throw new RequestNotFoundException();
        }

        if(Boolean.TRUE.equals(responseStatus)) {

            User user= new User();
                user.setUserName(masterUserRequests.get().getUserName());
                user.setPassword(
                        encodePassword(masterUserRequests.get().getPassword()));
                user.setFirstName(masterUserRequests.get().getFirstName());
                user.setLastName(masterUserRequests.get().getLastName());
                user.setEmailId(masterUserRequests.get().getEmailId());
                user.setPhoneNumber(masterUserRequests.get().getPhoneNumber());
                user.setAuthorityId(2L);
                user.setBaseLocation(masterUserRequests.get().getBaseLocation());
                user.setUniqueIdentificationNumber(
                        generateUniqueIdentificationNumber(user));

                masterUserRequests.get().setResponseStatus(true);
                masterUserRequests.get().setSolutionStatus(true);
                masterUserRequests.get().setDateSolved(new Timestamp(System.currentTimeMillis()));

            User userReturned= userProcedure.saveAndFlush(user);
            masterUserRequestsProcedure.save(masterUserRequests.get());

            ResponseTemplateVo masterUserResponseTemplateVo= new ResponseTemplateVo();
            masterUserResponseTemplateVo.setUser(modelMapper.map(userReturned, UserResponseDto.class));
            masterUserResponseTemplateVo.setResponseStatus(
                    Constants.MASTER_USER_REGISTRATION_REQUEST_SUCCESSFUL_STRING);

            mailSenderServiceFacade.sendMail(userReturned.getEmailId(), Constants.EMAIL_START_WARNING +
                            Constants.MASTER_USER_REGISTRATION_SUCCESSFUL_RESPONSE +
                            Constants.EMAIL_USERID + userReturned.getUserId() +
                            Constants.EMAIL_USERNAME + userReturned.getUserName() +
                            Constants.EMAIL_CONTACT + userReturned.getPhoneNumber() +
                            Constants.EMAIL_UNIQUE_IDENTIFICATION_NUMBER +
                            userReturned.getUniqueIdentificationNumber() +
                            Constants.EMAIL_END_WARNING
                    , Constants.MASTER_USER_REGISTRATION_SUCCESSFUL_RESPONSE_SUBJECT);

            return new ResponseEntity<>(masterUserResponseTemplateVo, HttpStatus.OK);
        } else {

                masterUserRequests.get().setResponseStatus(true);
                masterUserRequests.get().setSolutionStatus(false);
                masterUserRequests.get().setDateSolved(new Timestamp(System.currentTimeMillis()));

            masterUserRequestsProcedure.save(masterUserRequests.get());

            mailSenderServiceFacade.sendMail(masterUserRequests.get().getEmailId(),
                    Constants.EMAIL_START_WARNING +
                    Constants.MASTER_USER_REGISTRATION_FAILED_RESPONSE +
                    Constants.EMAIL_END_WARNING
                    , Constants.MASTER_USER_REGISTRATION_FAILED_RESPONSE_SUBJECT);

            return new ResponseEntity<>(Constants.MASTER_USER_REGISTRATION_DENIED +
                    masterUserRequests.get().getFirstName()+ " "+ masterUserRequests.get().getLastName()
                    , HttpStatus.OK);
        }
    }

    @Override
    public MasterUserResponseDto getMasterAccountRequest(Long userId, Long requestId) {

        Optional<User> user= userProcedure.findById(userId);
        Optional<MasterUserRequests> masterUserRequests= masterUserRequestsProcedure.findById(requestId);

        if(user.isEmpty() || user.get().getUserId() != 1L) {
            throw new UserAuthorityForbiddenException();
        }else if(masterUserRequests.isEmpty()) {
            throw new RequestNotFoundException();
        }

        return modelMapper.map(masterUserRequests, MasterUserResponseDto.class);
    }

    @Override
    public MasterUserRequestsResponseVo getMasterAccountRequestList(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Long userId) {

        Sort sort= null;

        if(sortDir.equalsIgnoreCase("ASC")) {
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }

        Optional<User> user= userProcedure.findById(userId);

        if(user.isEmpty() || user.get().getUserId() != 1L) {
            throw new UserAuthorityForbiddenException();
        }

        //Pagination
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        Page<MasterUserRequests> masterUserRequests= masterUserRequestsProcedure.findAll(pageable);
        List<MasterUserRequests> masterUserRequestsList= masterUserRequests.getContent();

        List<MasterUserResponseDto> masterUserResponseDtoList= masterUserRequestsList.stream()
                .map(request-> modelMapper.map(request, MasterUserResponseDto.class))
                .collect(Collectors.toList());

        MasterUserRequestsResponseVo masterUserRequestsResponseVo= new MasterUserRequestsResponseVo();
            masterUserRequestsResponseVo.setMasterUserResponseDtoList(masterUserResponseDtoList);
            masterUserRequestsResponseVo.setPageNumber(masterUserRequests.getNumber());
            masterUserRequestsResponseVo.setPageSize(masterUserRequests.getSize());
            masterUserRequestsResponseVo.setTotalElements(masterUserRequests.getTotalElements());
            masterUserRequestsResponseVo.setTotalPages(masterUserRequests.getTotalPages());
            masterUserRequestsResponseVo.setLastPage(masterUserRequests.isLast());

        return masterUserRequestsResponseVo;
    }

    public String generateUniqueIdentificationNumber(User user) {

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
}
