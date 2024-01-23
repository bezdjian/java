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

  public static VehicleResponse toResponseModel(Vehicle vehicle) {
    return VehicleResponse.builder()
        .uuid(vehicle.getId().toString())
        .name(vehicle.getName())
        .tenant(vehicle.getTenant())
        .build();
  }

  @Override
  public String toString() {
    return "{" +
        "uuid='" + uuid + '\'' +
        ", name='" + name + '\'' +
        ", tenant='" + tenant + '\'' +
        '}';
  }
}
