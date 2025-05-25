package com.dustopia.ms_project.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ReaderResponse {
    private String id;
    private String username;
    @JsonProperty
    private String fullName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private String address;
    private  String email;
}
