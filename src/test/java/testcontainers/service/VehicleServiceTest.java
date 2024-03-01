package testcontainers.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testcontainers.entity.Vehicle;
import testcontainers.model.VehicleRequest;
import testcontainers.model.VehicleResponse;
import testcontainers.repository.VehicleRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class VehicleServiceTest {

  @InjectMocks
  private VehicleService service;
  @Mock
  private VehicleRepository repository;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void shouldSaveVehicle() {
    //Given
    String vehicleName = "SavedVehicle";
    String tenant = "vw";

    VehicleRequest vehicleRequest = VehicleRequest.builder()
      .name(vehicleName)
      .tenant(tenant)
      .build();

    when(repository.save(any(Vehicle.class))).thenReturn(Mono.just(
      VehicleResponse.builder()
        .name(vehicleName)
        .tenant(tenant)
        .classType("VW")
        .build()
    ));

    //When
    Mono<VehicleResponse> saved = service.save(vehicleRequest);

    //Then
    StepVerifier.create(saved)
      .assertNext(v -> assertEquals(vehicleName, v.getName()))
      .verifyComplete();
  }
}