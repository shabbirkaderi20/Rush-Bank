package com.rush.banking.userservice.service.impl;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.dto.UserResponseDto;
import com.rush.banking.userservice.entity.BaseLocationRequests;
import com.rush.banking.userservice.entity.User;
import com.rush.banking.userservice.exceptions.RequestNotFoundException;
import com.rush.banking.userservice.exceptions.UserAuthorityForbiddenException;
import com.rush.banking.userservice.exceptions.UserNotFoundException;
import com.rush.banking.userservice.procedure.BaseLocationProcedure;
import com.rush.banking.userservice.procedure.UserProcedure;
import com.rush.banking.userservice.responsevo.BaseLocationRequestsResponseVo;
import com.rush.banking.userservice.responsevo.ResponseTemplateVo;
import com.rush.banking.userservice.service.BaseLocationServiceFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service("BaseLocationServiceFacade")
public class BaseLocationServiceFacadeImpl implements BaseLocationServiceFacade {

    @Autowired
    private BaseLocationProcedure baseLocationProcedure;

    @Autowired
    private MailSenderServiceFacadeImpl mailSenderServiceFacade;

    @Autowired
    private UserProcedure userProcedure;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String baseLocationChangeRequest(Long userId, String emailId, String uniqueIdentificationNumber, String previousBaseLocation, String requestedBaseLocation) {

        BaseLocationRequests baseLocationRequests= new BaseLocationRequests();
            baseLocationRequests.setUserId(userId);
            baseLocationRequests.setUniqueIdentificationNumber(uniqueIdentificationNumber);
            baseLocationRequests.setPreviousBaseLocation(previousBaseLocation);
            baseLocationRequests.setRequestedBaseLocation(requestedBaseLocation);
            baseLocationRequests.setDateRaised(new Timestamp(System.currentTimeMillis()));
            baseLocationRequests.setResponseStatus(false);

        baseLocationProcedure.save(baseLocationRequests);

        mailSenderServiceFacade.sendMail(emailId,
                Constants.EMAIL_START_WARNING + Constants.BASE_LOCATION_REQUEST_REGISTERED +
                        requestedBaseLocation + Constants.EMAIL_END_WARNING,
                Constants.BASE_LOCATION_CHANGE_REQUEST_SUBJECT);

        return Constants.BASE_LOCATION_REQUEST_REGISTERED_STRING+ requestedBaseLocation;
    }

    @Override
    public ResponseTemplateVo baseLocationResponse(Long userId, Long requestId, Boolean responseStatus) {

        if(userId != 1L) {
            throw new UserAuthorityForbiddenException();
        }

        Optional<BaseLocationRequests> baseLocationRequests= baseLocationProcedure.findById(requestId);

        if(baseLocationRequests.isEmpty()) {
            throw new RequestNotFoundException();
        }
        Optional<User> user= userProcedure.findById(baseLocationRequests.get().getUserId());

        if(Boolean.TRUE.equals(responseStatus)) {

            if(user.isEmpty()) {
                throw new UserNotFoundException();
           }else {

               user.get().setBaseLocation(baseLocationRequests.get().getRequestedBaseLocation());
               baseLocationRequests.get().setResponseStatus(true);
               baseLocationRequests.get().setSolutionStatus(true);
               baseLocationRequests.get().setDateSolved(new Timestamp(System.currentTimeMillis()));

               User userReturned= userProcedure.saveAndFlush(user.get());
               baseLocationProcedure.save(baseLocationRequests.get());

               ResponseTemplateVo baseLocationResponseTemplateVo= new ResponseTemplateVo();
                    baseLocationResponseTemplateVo.setUser(modelMapper.map(userReturned, UserResponseDto.class));
                    baseLocationResponseTemplateVo.setResponseStatus( Constants.BASE_LOCATION_REQUEST_SUCCESSFUL +
                            baseLocationRequests.get().getRequestedBaseLocation());

               mailSenderServiceFacade.sendMail(userReturned.getEmailId(), Constants.EMAIL_START_WARNING +
                       Constants.BASE_LOCATION_SUCCESSFUL_RESPONSE +
                               userReturned.getBaseLocation() + Constants.EMAIL_END_WARNING
                       , Constants.BASE_LOCATION_SUCCESSFUL_RESPONSE_SUBJECT);

               return baseLocationResponseTemplateVo;
           }
        } else {

            if(user.isEmpty()) {
                throw new UserNotFoundException();
            } else {

                baseLocationRequests.get().setResponseStatus(true);
                baseLocationRequests.get().setSolutionStatus(false);
                baseLocationRequests.get().setDateSolved(new Timestamp(System.currentTimeMillis()));

                baseLocationProcedure.save(baseLocationRequests.get());

                ResponseTemplateVo baseLocationResponseTemplateVo = new ResponseTemplateVo();
                baseLocationResponseTemplateVo.setUser(modelMapper.map(user.get(), UserResponseDto.class));
                baseLocationResponseTemplateVo.setResponseStatus(Constants.BASE_LOCATION_REQUEST_FAILED +
                                user.get().getBaseLocation());

                mailSenderServiceFacade.sendMail(user.get().getEmailId(), Constants.EMAIL_START_WARNING +
                    Constants.BASE_LOCATION_FAILED_RESPONSE +
                            user.get().getBaseLocation() + Constants.EMAIL_END_WARNING
                        , Constants.BASE_LOCATION_FAILED_RESPONSE_SUBJECT);

                return baseLocationResponseTemplateVo;
            }
        }
    }

    @Override
    public BaseLocationRequestsResponseVo getRequestList(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Long userId) {

        Sort sort= null;

        if(sortDir.equalsIgnoreCase("ASC")) {
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }

        Optional<User> userReturned= userProcedure.findById(userId);

        if(userReturned.isEmpty() || userReturned.get().getUserId() != 1L) {
            throw new UserAuthorityForbiddenException();
        }

        //Pagination
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        Page<BaseLocationRequests> requestsReturned= baseLocationProcedure.findAll(pageable);
        List<BaseLocationRequests> requestsList= requestsReturned.getContent();

        BaseLocationRequestsResponseVo baseLocationRequestsResponseVo= new BaseLocationRequestsResponseVo();
            baseLocationRequestsResponseVo.setBaseLocationRequestsList(requestsList);
            baseLocationRequestsResponseVo.setPageNumber(requestsReturned.getNumber());
            baseLocationRequestsResponseVo.setPageSize(requestsReturned.getSize());
            baseLocationRequestsResponseVo.setTotalElements(requestsReturned.getTotalElements());
            baseLocationRequestsResponseVo.setTotalPages(requestsReturned.getTotalPages());
            baseLocationRequestsResponseVo.setLastPage(requestsReturned.isLast());

        return baseLocationRequestsResponseVo;
    }
}
