package com.dustopia.ms_project.model.dto.response;

import com.dustopia.ms_project.model.entity.BookReturning;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnResponse {
      // Thông tin trả sách
    private float totalLateFee;       // Tổng phí trả muộn
    private float totalDamageFee;     // Tổng phí đền bù
}
