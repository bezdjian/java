package testcontainers.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import testcontainers.entity.Vehicle;
import testcontainers.entity.VehicleAudi;
import testcontainers.entity.VehicleSeat;
import testcontainers.entity.VehicleVW;
import testcontainers.exceptions.TenantNotFoundException;
import testcontainers.model.VehicleRequest;
import testcontainers.model.VehicleResponse;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

  private final ReactiveMongoTemplate mongoTemplate;

  public Mono<VehicleResponse> save(VehicleRequest vehicleRequest) {
    // sim-vehicle-view-vw-iot, sim-vehicle-view-audi-iot etc.
    String environment = "iot"; // gets from system env
    String collectionName = String.format("sim-vehicle-view-%s-%s", vehicleRequest.getTenant().replace("/", "-"),
      environment);
    Vehicle vehicle = getVehicleByTenant(vehicleRequest);

    return mongoTemplate.save(vehicle, collectionName)
      .doOnSuccess(saved -> log.info("Successfully saved vehicle: {}", saved.toString()))
      .map(VehicleResponse::toResponseModel);
  }

  private Vehicle getVehicleByTenant(VehicleRequest vehicleRequest) {
    return switch (vehicleRequest.getTenant()) {
      case "vw" -> VehicleVW.builder().id(UUID.randomUUID()).name(vehicleRequest.getName())
        .tenant(vehicleRequest.getTenant()).model("ID5").classType("VW")
        .build();
      case "audi" -> VehicleAudi.builder().id(UUID.randomUUID()).name(vehicleRequest.getName())
        .tenant(vehicleRequest.getTenant()).classType("Audi")
        .build();
      case "seat" -> VehicleSeat.builder().id(UUID.randomUUID()).name(vehicleRequest.getName())
        .tenant(vehicleRequest.getTenant()).classType("Seat")
        .build();
      default -> throw new TenantNotFoundException("Unknown tenant " + vehicleRequest.getTenant());
    };
  }
}
