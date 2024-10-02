package com.rush.banking.userservice.responsevo;

import com.rush.banking.userservice.entity.AuthorityManagement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityManagementResponseVo {

    private List<AuthorityManagement> authorityManagementList;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
