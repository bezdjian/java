package testcontainers.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import testcontainers.entity.Vehicle;
import testcontainers.model.VehicleResponse;

@Slf4j
@Repository
@RequiredArgsConstructor
public class VehicleRepository {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public Mono<VehicleResponse> save(Vehicle vehicle) {
    String environment = "iot"; // gets from system env
    String collectionName = String.format("sim-vehicle-view-%s-%s", vehicle.getTenant().replace("/", "-"),
      environment);
    return reactiveMongoTemplate.save(vehicle, collectionName)
      .map(VehicleResponse::toResponseModel);
  }

  public Flux<VehicleResponse> findAll() {
    String collectionName = String.format("sim-vehicle-view-%s-%s", "audi".replace("/", "-"),
      "iot");
    return reactiveMongoTemplate.findAll(Vehicle.class, collectionName)
      .map(VehicleResponse::toResponseModel);
  }
}
