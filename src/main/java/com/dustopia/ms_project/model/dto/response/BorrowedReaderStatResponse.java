package com.dustopia.ms_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedReaderStatResponse {
    private String id;
    private String username;
    private String fullName;
    private String address;
    private LocalDate dateOfBirth;
    private String email;
    private Integer totalBorrow;
}
