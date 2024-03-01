package testcontainers.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import testcontainers.config.ContainersConfig;
import testcontainers.entity.Vehicle;
import testcontainers.model.VehicleResponse;

import java.util.UUID;

class VehicleRepositoryTest {

  @Container
  // Since 3.1.0, @ServiceConnection automatically configures the necessary Spring Boot properties
  // for the supporting containers. No need for @DynamicPropertySource.
  @ServiceConnection
  static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse(ContainersConfig.fullImageName))
    .withReuse(true);

  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;

  @Test
  void shouldSaveVehicle() {
    //Given
    final VehicleRepository vehicleRepository = new VehicleRepository(reactiveMongoTemplate);
    Vehicle vehicleVw = Vehicle.builder()
      .id(UUID.randomUUID())
      .name("VW")
      .tenant("vw")
      .build();

    Vehicle vehicleAudi = Vehicle.builder()
      .id(UUID.randomUUID())
      .name("Audi")
      .tenant("audi")
      .build();

    //When
    VehicleResponse saved1 = vehicleRepository.save(vehicleVw).block();
    VehicleResponse saved2 = vehicleRepository.save(vehicleAudi).block();

    //Then
    assert saved1 != null;
    assert saved2 != null;
    Flux<VehicleResponse> allVehicles = vehicleRepository.findAll();
    StepVerifier.create(allVehicles)
      .expectNext(saved1)
      .expectNext(saved2)
      .verifyComplete();
  }
}