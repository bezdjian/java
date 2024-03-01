package testcontainers.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public class Vehicle {

  @Id
  protected UUID id;
  protected String name;
  protected String tenant;
}
