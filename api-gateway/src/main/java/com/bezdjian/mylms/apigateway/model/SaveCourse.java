package com.bezdjian.mylms.apigateway.model;


import com.bezdjian.mylms.apigateway.dto.CategoryDTO;
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