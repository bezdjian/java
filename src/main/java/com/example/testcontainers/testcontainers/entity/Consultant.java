package com.example.testcontainers.testcontainers.entity;

import com.example.testcontainers.testcontainers.model.ConsultantResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Consultants")
public class Consultant {

  @Id
  @UuidGenerator
  private UUID id;

  private String name;
  private String technology;

  public static Consultant toEntity(ConsultantResponse request) {
    return Consultant.builder()
        .name(request.getName())
        .technology(request.getTechnology())
        .build();
  }
}
