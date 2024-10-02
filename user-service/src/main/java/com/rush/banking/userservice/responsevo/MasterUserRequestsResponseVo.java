package com.rush.banking.userservice.responsevo;

import com.rush.banking.userservice.dto.MasterUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterUserRequestsResponseVo {

    private List<MasterUserResponseDto> masterUserResponseDtoList;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
