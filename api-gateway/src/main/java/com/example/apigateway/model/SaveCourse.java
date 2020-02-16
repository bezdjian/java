package com.example.apigateway.model;


import com.example.apigateway.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveCourse {

  private String coursename;
  private String description;
  private String idnumber;
  private String image;
  private Long price;
  private CategoryDTO category;
}