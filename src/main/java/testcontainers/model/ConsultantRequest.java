package testcontainers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testcontainers.entity.Consultant;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantRequest {

  private String name;
  private String technology;

  public static Consultant toEntity(ConsultantRequest consultant) {
    return Consultant.builder()
        .id(UUID.randomUUID())
        .name(consultant.getName())
        .technology(consultant.getTechnology())
        .build();
  }
}
