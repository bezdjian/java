package testcontainers.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import testcontainers.model.ConsultantResponse;

import java.util.UUID;

@Document("Consultant")
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Consultant {

  @Id
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
