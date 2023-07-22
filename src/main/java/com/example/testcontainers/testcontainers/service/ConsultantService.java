package com.example.testcontainers.testcontainers.service;

import com.example.testcontainers.testcontainers.entity.Consultant;
import com.example.testcontainers.testcontainers.model.ConsultantRequest;
import com.example.testcontainers.testcontainers.model.ConsultantResponse;
import com.example.testcontainers.testcontainers.repository.ConsultantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsultantService {

  @Autowired
  private ConsultantRepository consultantRepository;

  public Flux<ConsultantResponse> findAll() {
    return Flux.defer(() -> Flux.fromIterable(this.consultantRepository.findAll()
        .stream()
        .map(ConsultantResponse::toModel)
        .collect(Collectors.toList())
    ));
  }

  public Mono<ConsultantResponse> save(ConsultantRequest consultant) {
    return Mono.fromCallable(() -> {
      Consultant saved = consultantRepository.save(ConsultantRequest.toEntity(consultant));
      log.info("Successfully saved Consultant with ID: {}", saved.getId());
      return ConsultantResponse.toModel(saved);
    });
  }

  public void delete(String uuid) {
    consultantRepository.deleteById(UUID.fromString(uuid));
    log.info("Successfully deleted Consultant with ID {}", uuid);
  }

  public Flux<ConsultantResponse> findConsultantsByTechnology(String technology) {
    return Flux.defer(() -> Flux.fromIterable(consultantRepository.findConsultantsByTechnology(technology)
        .stream()
        .map(ConsultantResponse::toModel)
        .collect(Collectors.toList())
    ));
  }
}
