package com.dustopia.ms_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookResponse {
    private String bookName;
    private LocalDateTime borrowTime;
    private LocalDateTime expectedReturnTime;
    private LocalDateTime returnTime;
    private Float deposit;
    private String note;
    private String damageDescription;
    private Float compensate;
}
