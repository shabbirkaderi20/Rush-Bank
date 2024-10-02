package com.rush.banking.userservice.service.impl;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.dto.UserResponseDto;
import com.rush.banking.userservice.entity.AuthorityManagement;
import com.rush.banking.userservice.entity.DemotedAuthorityProcess;
import com.rush.banking.userservice.entity.User;
import com.rush.banking.userservice.exceptions.OperationCannotBePerformedException;
import com.rush.banking.userservice.exceptions.RequestNotFoundException;
import com.rush.banking.userservice.exceptions.UserAuthorityForbiddenException;
import com.rush.banking.userservice.exceptions.UserNotFoundException;
import com.rush.banking.userservice.procedure.AuthorityManagementProcedure;
import com.rush.banking.userservice.procedure.DemotedAuthorityProcedure;
import com.rush.banking.userservice.procedure.UserProcedure;
import com.rush.banking.userservice.responsevo.AuthorityManagementResponseVo;
import com.rush.banking.userservice.responsevo.ResponseTemplateVo;
import com.rush.banking.userservice.service.AuthorityManagementServiceFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service("AuthorityManagementServiceFacade")
public class AuthorityManagementServiceFacadeImpl implements AuthorityManagementServiceFacade {

    @Autowired
    private AuthorityManagementProcedure authorityManagementProcedure;

    @Autowired
    private MailSenderServiceFacadeImpl mailSenderServiceFacade;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserProcedure userProcedure;

    @Autowired
    private DemotedAuthorityProcedure demotedAuthorityProcedure;

    @Override
    public String requestUserAuthorityRequest(Long userId) {

        Optional<User> user= userProcedure.findById(userId);

        if(user.isEmpty())
            throw new UserNotFoundException();

        AuthorityManagement authorityManagement= new AuthorityManagement();
            authorityManagement.setRequestedAuthority(2L);
            authorityManagement.setPreviousAuthority(3L);
            authorityManagement.setDateRaised(new Timestamp(System.currentTimeMillis()));
            authorityManagement.setUniqueIdentificationNumber(user.get().getUniqueIdentificationNumber());
            authorityManagement.setUserId(userId);
            authorityManagement.setResponseStatus(false);

        authorityManagementProcedure.save(authorityManagement);

        mailSenderServiceFacade.sendMail(user.get().getEmailId(),
                Constants.EMAIL_START_WARNING + Constants.AUTHORITY_RAISE_REQUEST_REGISTERED +
                        Constants.EMAIL_END_WARNING,
                Constants.AUTHORITY_RAISE_REQUEST_SUBJECT);

        return Constants.AUTHORITY_RAISE_REQUEST_REGISTERED_STRING;
    }

    @Override
    public ResponseEntity<?> processUpraiseAuthorityRequest(Long userId, Long requestId, Boolean responseStatus) {

        if(userId!= 1L) {
            throw new UserAuthorityForbiddenException();
        }

        Optional<AuthorityManagement> authorityManagement= authorityManagementProcedure.findById(requestId);

        if(authorityManagement.isEmpty()) {
            throw new RequestNotFoundException();
        }

        Optional<User> user= userProcedure.findById(authorityManagement.get().getUserId());

        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        if(Boolean.TRUE.equals(responseStatus)) {

            user.get().setAuthorityId(authorityManagement.get().getRequestedAuthority());
            authorityManagement.get().setResponseStatus(true);
            authorityManagement.get().setSolutionStatus(true);
            authorityManagement.get().setDateSolved(new Timestamp(System.currentTimeMillis()));

            User userReturned= userProcedure.saveAndFlush(user.get());
            authorityManagementProcedure.saveAndFlush(authorityManagement.get());

            ResponseTemplateVo authorityRaised= new ResponseTemplateVo();
                authorityRaised.setUser(modelMapper.map(userReturned, UserResponseDto.class));
                authorityRaised.setResponseStatus(Constants.AUTHORITY_RAISE_REQUEST_SUCCESSFUL_STRING);

            mailSenderServiceFacade.sendMail(userReturned.getEmailId(),
                    Constants.EMAIL_START_WARNING + Constants.AUTHORITY_RAISE_REQUEST_SUCCESSFUL +
                            Constants.EMAIL_END_WARNING
                    , Constants.AUTHORITY_RAISE_REQUEST_SUCCESSFUL_SUBJECT);

            return new ResponseEntity<>(authorityRaised, HttpStatus.OK);
        } else {

            authorityManagement.get().setResponseStatus(true);
            authorityManagement.get().setSolutionStatus(false);
            authorityManagement.get().setDateSolved(new Timestamp(System.currentTimeMillis()));

            authorityManagementProcedure.saveAndFlush(authorityManagement.get());

            mailSenderServiceFacade.sendMail(user.get().getEmailId(),
                    Constants.EMAIL_START_WARNING + Constants.AUTHORITY_RAISE_REQUEST_DENIED +
                            Constants.EMAIL_END_WARNING
                    , Constants.AUTHORITY_RAISE_REQUEST_DENIED_SUBJECT);

            return new ResponseEntity<>(Constants.AUTHORITY_RAISE_REQUEST_DENIED_STRING, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public String degradeAuthority(Long userId, Long clientId) {

        if(userId!= 1L) {
            throw new UserAuthorityForbiddenException();
        }

        Optional<User> user= userProcedure.findById(clientId);

        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }
        if((user.get().getAuthorityId())!= 2L) {
            throw new OperationCannotBePerformedException();
        }

            user.get().setAuthorityId(3L);
        userProcedure.saveAndFlush(user.get());

        DemotedAuthorityProcess demotedAuthorityProcess= new DemotedAuthorityProcess();
            demotedAuthorityProcess.setUserId(user.get().getUserId());
            demotedAuthorityProcess.setFullName(user.get().getFirstName()+ " "+ user.get().getLastName());
            demotedAuthorityProcess.setDateRegistered(new Timestamp(System.currentTimeMillis()));

        demotedAuthorityProcedure.saveAndFlush(demotedAuthorityProcess);

        mailSenderServiceFacade.sendMail(user.get().getEmailId(), Constants.EMAIL_START_WARNING +
                Constants.EMAIL_DEGRADED_AUTHORITY + Constants.EMAIL_END_WARNING,
                Constants.DEGRADE_AUTHORITY_SUBJECT);

        return Constants.DEGRADE_AUTHORITY_STRING + user.get().getFirstName()+ " " + user.get().getLastName();
    }

    @Override
    public AuthorityManagementResponseVo getRequestList(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Long userId) {

        Sort sort= null;

        if(sortDir.equalsIgnoreCase("ASC")) {
            sort= sort.by(sortBy).ascending();
        }else {
            sort= sort.by(sortBy).descending();
        }

        Optional<User> userReturned= userProcedure.findById(userId);

        if(userReturned.isEmpty() || userReturned.get().getUserId() != 1L) {
            throw new UserAuthorityForbiddenException();
        }

        //Pagination
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        Page<AuthorityManagement> requestsReturned= authorityManagementProcedure.findAll(pageable);
        List<AuthorityManagement> requestsList= requestsReturned.getContent();

        AuthorityManagementResponseVo authorityManagementResponseVo= new AuthorityManagementResponseVo();
            authorityManagementResponseVo.setAuthorityManagementList(requestsList);
            authorityManagementResponseVo.setPageNumber(requestsReturned.getNumber());
            authorityManagementResponseVo.setPageSize(requestsReturned.getSize());
            authorityManagementResponseVo.setTotalElements(requestsReturned.getTotalElements());
            authorityManagementResponseVo.setTotalPages(requestsReturned.getTotalPages());
            authorityManagementResponseVo.setLastPage(requestsReturned.isLast());

        return authorityManagementResponseVo;
    }
}