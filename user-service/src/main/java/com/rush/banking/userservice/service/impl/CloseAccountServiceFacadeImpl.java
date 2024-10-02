package com.rush.banking.userservice.service.impl;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.dto.CloseUserRequestsResponseDto;
import com.rush.banking.userservice.entity.CloseUserRequests;
import com.rush.banking.userservice.entity.User;
import com.rush.banking.userservice.exceptions.*;
import com.rush.banking.userservice.procedure.CloseUserProcedure;
import com.rush.banking.userservice.procedure.UserProcedure;
import com.rush.banking.userservice.responsevo.CloseUserRequestsResponseVo;
import com.rush.banking.userservice.service.CloseAccountServiceFacade;
import com.rush.banking.userservice.service.MailSenderServiceFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("CloseAccountServiceFacade")
public class CloseAccountServiceFacadeImpl implements CloseAccountServiceFacade {

    @Autowired
    private CloseUserProcedure closeUserProcedure;

    @Autowired
    private UserProcedure userProcedure;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MailSenderServiceFacade mailSenderServiceFacade;

    @Override
    public String closeUserRequest(Long userId) {

        Optional<CloseUserRequests> existingUserRequests = closeUserProcedure.findByUserId(userId);

        if(existingUserRequests.isPresent()) {
            throw new RequestAlreadyRegisteredException();
        }

        Optional<User> user= userProcedure.findById(userId);

        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        CloseUserRequests closeUserRequest = modelMapper.map(user.get(), CloseUserRequests.class);
            closeUserRequest.setRequestDate(new Timestamp(System.currentTimeMillis()));
            closeUserRequest.setIsSolved(false);

        closeUserProcedure.saveAndFlush(closeUserRequest);

        mailSenderServiceFacade.sendMail(closeUserRequest.getEmailId(),
                Constants.EMAIL_START_WARNING + Constants.EMAIL_CLOSE_USER_ACCOUNT_REQUEST +
                        Constants.EMAIL_END_WARNING, Constants.EMAIL_CLOSE_USER_ACCOUNT_SUBJECT);

        return Constants.ACCOUNT_CLOSE_REQUEST_SUBMITTED_STRING;
    }

    @Override
    public String closeUserAccountResponse(Long userId, Long requestId, Boolean responseStatus) {

        if(userId != 1L) {
            throw new UserAuthorityForbiddenException();
        }

        Optional<CloseUserRequests> closeUserRequests = closeUserProcedure.findById(requestId);

        if(closeUserRequests.isEmpty()) {
            throw new RequestNotFoundException();

        }else if(closeUserRequests.get().getIsSolved()) {
            throw new AccountActionTakenException();
        }

        if(Boolean.TRUE.equals(responseStatus)) {

            closeUserRequests.get().setIsSolved(true);
            closeUserRequests.get().setIsApproved(true);
            closeUserRequests.get().setResponseDate(new Timestamp(System.currentTimeMillis()));

            closeUserProcedure.saveAndFlush(closeUserRequests.get());
            userProcedure.deleteById(closeUserRequests.get().getUserId());

            mailSenderServiceFacade.sendMail(closeUserRequests.get().getEmailId(),
                    Constants.EMAIL_START_WARNING + Constants.EMAIL_ACCOUNT_CLOSED_SUCCESSFULLY +
                            Constants.EMAIL_END_WARNING, Constants.EMAIL_ACCOUNT_CLOSED_SUCCESSFUL_SUBJECT);

            return Constants.ACCOUNT_CLOSED_RESPONSE_SUCCESSFUL_STRING;
        }else {

            closeUserRequests.get().setIsSolved(true);
            closeUserRequests.get().setIsApproved(false);
            closeUserRequests.get().setResponseDate(new Timestamp(System.currentTimeMillis()));

            closeUserProcedure.saveAndFlush(closeUserRequests.get());

            mailSenderServiceFacade.sendMail(closeUserRequests.get().getEmailId(),
                    Constants.EMAIL_START_WARNING + Constants.EMAIL_ACCOUNT_CLOSED_REJECTED +
                            Constants.EMAIL_END_WARNING, Constants.EMAIL_ACCOUNT_CLOSED_REJECTED_SUBJECT);

            return Constants.ACCOUNT_CLOSED_RESPONSE_REJECTED_STRING;
        }
    }

    @Override
    public CloseUserRequestsResponseVo getClosedAccounts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Long userId) {

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
        Page<CloseUserRequests> closeUserRequestsReturned= closeUserProcedure.findAll(pageable);
        List<CloseUserRequests> closeUserRequestsList= closeUserRequestsReturned.getContent();

        List<CloseUserRequestsResponseDto> closeUserRequestsResponseDtoList= new ArrayList<>();

        closeUserRequestsList.forEach(closeAccountRequest-> {
            CloseUserRequestsResponseDto closeUserRequestsResponseDto= modelMapper.
                    map(closeAccountRequest, CloseUserRequestsResponseDto.class);

            closeUserRequestsResponseDtoList.add(closeUserRequestsResponseDto);
        });

        CloseUserRequestsResponseVo closeUserRequestsResponseVo= new CloseUserRequestsResponseVo();
            closeUserRequestsResponseVo.setCloseUserRequestsResponseDtoList(closeUserRequestsResponseDtoList);
            closeUserRequestsResponseVo.setPageNumber(closeUserRequestsReturned.getNumber());
            closeUserRequestsResponseVo.setPageSize(closeUserRequestsReturned.getSize());
            closeUserRequestsResponseVo.setTotalElements(closeUserRequestsReturned.getTotalElements());
            closeUserRequestsResponseVo.setTotalPages(closeUserRequestsReturned.getTotalPages());
            closeUserRequestsResponseVo.setLastPage(closeUserRequestsReturned.isLast());

        return closeUserRequestsResponseVo;
    }

    @Override
    public CloseUserRequestsResponseDto getClosedAccount(Long userId, Long requestId) {

        Optional<User> user= userProcedure.findById(userId);
        Optional<CloseUserRequests> request= closeUserProcedure.findById(requestId);

        if(user.isEmpty() || user.get().getUserId() != 1L) {
            throw new UserAuthorityForbiddenException();
        }else if(request.isEmpty()) {
            throw new RequestNotFoundException();
        }

        return modelMapper.
                map(closeUserProcedure.findById(requestId), CloseUserRequestsResponseDto.class);
    }

}
