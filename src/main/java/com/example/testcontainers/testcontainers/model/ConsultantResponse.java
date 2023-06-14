package com.example.testcontainers.testcontainers.model;

import com.example.testcontainers.testcontainers.entity.Consultant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantResponse {

  private String uuid;
  private String name;
  private String technology;

  public static ConsultantResponse toModel(Consultant consultant) {
    return ConsultantResponse.builder()
        .uuid(consultant.getId().toString())
        .name(consultant.getName())
        .technology(consultant.getTechnology())
        .build();
  }
}
