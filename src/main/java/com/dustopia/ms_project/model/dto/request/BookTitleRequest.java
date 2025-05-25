package com.dustopia.ms_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookTitleRequest {
  private String name;
  private String author;
  private String category;
  private Integer publicYear;
  private String publisher;
  private String description;
}
