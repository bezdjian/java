package testcontainers.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import testcontainers.entity.Vehicle;
import testcontainers.entity.VehicleAudi;
import testcontainers.entity.VehicleSeat;
import testcontainers.entity.VehicleVW;
import testcontainers.exceptions.TenantNotFoundException;
import testcontainers.model.VehicleRequest;
import testcontainers.model.VehicleResponse;
import testcontainers.repository.VehicleRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

  private final VehicleRepository vehicleRepository;

  public Mono<VehicleResponse> save(VehicleRequest vehicleRequest) {
    Vehicle vehicle = getVehicleByTenant(vehicleRequest);
    return vehicleRepository.save(vehicle)
      .doFirst(() -> log.info("Saving vehicle: {}", vehicle))
      .doOnSuccess(saved -> log.info("Successfully saved vehicle: {}", saved.toString()));
  }

  public Flux<VehicleResponse> findAll() {
    return vehicleRepository.findAll()
      .doFirst(() -> log.info("Getting all vehicles"))
      .doOnComplete(() -> log.info("Successfully got all vehicles"));
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
