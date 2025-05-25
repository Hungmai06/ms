package com.dustopia.ms_project.model.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookBorrowResponse {
    private String id;
    private LocalDateTime time;
    private Float deposit;
    private String note;
    private String nameLibrarian;
    private String nameReader;
}
