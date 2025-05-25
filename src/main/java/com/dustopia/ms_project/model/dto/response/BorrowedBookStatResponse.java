package com.dustopia.ms_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookStatResponse {
    private String bookTitleId;
    private String name;
    private String author;
    private Integer publicYear;
    private String publisher;
    private String category;
    private Integer totalBorrow;
}
