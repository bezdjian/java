package testcontainers.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import testcontainers.model.ConsultantRequest;
import testcontainers.model.ConsultantResponse;
import testcontainers.repository.ConsultantRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultantService {

  private final ConsultantRepository consultantRepository;

  public Flux<ConsultantResponse> findAll() {
    return this.consultantRepository.findAll()
        .map(ConsultantResponse::toModel);
  }

  public Mono<ConsultantResponse> save(ConsultantRequest consultant) {
    return consultantRepository.save(ConsultantRequest.toEntity(consultant))
        .doOnSuccess(saved -> log.info("Successfully saved Consultant with ID: {}", saved.getId()))
        .map(ConsultantResponse::toModel);
  }

  public void delete(String uuid) {
    consultantRepository.deleteById(UUID.fromString(uuid)).subscribe();
    log.info("Successfully deleted Consultant with ID {}", uuid);
  }

  public Flux<ConsultantResponse> findConsultantsByTechnology(String technology) {
    return consultantRepository.findConsultantByTechnology(technology)
        .map(ConsultantResponse::toModel);
  }
}
