package testcontainers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import testcontainers.model.VehicleRequest;
import testcontainers.model.VehicleResponse;
import testcontainers.service.VehicleService;

@Slf4j
@Tag(name = "Vehicle")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VehicleController {

  private final VehicleService vehicleService;

  @PostMapping("/vehicle")
  public Mono<VehicleResponse> saveVehicle(@RequestBody VehicleRequest vehicleRequest) {
    return vehicleService.save(vehicleRequest)
        .doOnError(error -> log.info("Exception while saving a vehicle!: {}", error.getMessage(), error));
  }
}
