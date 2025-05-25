package com.dustopia.ms_project.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @JsonProperty
    private String book_title_id;
    @JsonProperty
    private String book_importing_id;
}
