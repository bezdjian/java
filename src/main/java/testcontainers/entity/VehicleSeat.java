package testcontainers.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
//@Document("vehicle-seat")
public class VehicleSeat extends Vehicle {

  private final String classType;

}
