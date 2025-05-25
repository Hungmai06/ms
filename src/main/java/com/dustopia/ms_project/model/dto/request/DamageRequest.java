package com.dustopia.ms_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DamageRequest {
    private String borrowedBookId;   // ID của BorrowedBook bị hỏng
    private String description;    // Mô tả hỏng hóc
    private Float compensation;    // Tiền bồi thường
}
