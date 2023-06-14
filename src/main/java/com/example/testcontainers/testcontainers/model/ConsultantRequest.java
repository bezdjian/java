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
public class ConsultantRequest {

  private String name;
  private String technology;

  public static Consultant toEntity(ConsultantRequest consultant) {
    return Consultant.builder()
        .name(consultant.getName())
        .technology(consultant.getTechnology())
        .build();
  }
}
