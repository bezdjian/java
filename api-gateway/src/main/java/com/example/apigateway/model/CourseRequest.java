package com.example.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseRequest {

  private Long id;
  private String coursename;
  private String description;
  private String idnumber;
  private String image;
  private Long price;
  private Long category;
}
