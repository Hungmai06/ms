package com.dustopia.ms_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookKPIResponse {
    private Integer total;
    private Integer totalBorrow;
    private Integer totalReturn;
    private Integer totalCompensate;
}
