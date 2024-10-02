package com.rush.banking.userservice.responsevo;

import com.rush.banking.userservice.entity.BaseLocationRequests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseLocationRequestsResponseVo {

    private List<BaseLocationRequests> baseLocationRequestsList;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
