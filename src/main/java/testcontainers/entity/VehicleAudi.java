package testcontainers.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
//@Document("vehicle-audi")
public class VehicleAudi extends Vehicle {

  private final String classType;

}
