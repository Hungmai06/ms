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
public class BookResponse {
    private String Id;
    private String name;
    private String author;
    private String category;
    private Integer publicYear;
    private String publisher;
    private String description;
    private String nameSupplier;
}
