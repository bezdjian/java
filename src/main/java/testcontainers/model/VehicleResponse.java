package testcontainers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testcontainers.entity.Vehicle;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

  private String uuid;
  private String name;
  private String tenant;
  private String classType;

  public static VehicleResponse toResponseModel(Vehicle vehicle) {
    return VehicleResponse.builder()
        .uuid(vehicle.getId().toString())
        .name(vehicle.getName())
        .tenant(vehicle.getTenant())
        .classType(vehicle.getClass().getSimpleName())
        .build();
  }

  @Override
  public String toString() {
    return "{" +
        "uuid='" + uuid + '\'' +
        ", name='" + name + '\'' +
        ", tenant='" + tenant + '\'' +
        ", classType='" + classType + '\'' +
        '}';
  }
}
