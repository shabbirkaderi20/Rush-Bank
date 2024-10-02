package com.rush.banking.userservice.responsevo;

import com.rush.banking.userservice.dto.CloseUserRequestsResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloseUserRequestsResponseVo {

    private List<CloseUserRequestsResponseDto> closeUserRequestsResponseDtoList;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
