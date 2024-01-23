package testcontainers.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
//@Document("vehicle-vw")
public class VehicleVW extends Vehicle {

  private final String classType;
  private final String model;

  @Override
  public String toString() {
    return "VehicleVW{" +
        "classType='" + classType + '\'' +
        ", model='" + model + '\'' +
        ", id=" + id +
        ", name='" + name + '\'' +
        ", tenant='" + tenant + '\'' +
        '}';
  }
}
