package com.bezdjian.mylms.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    private Long id;
    private String courseName;
    private String description;
    private String idNumber;
    private String image;
    private Long price;
    private Category category;
}
