package com.example.testcontainers.testcontainers.service;

import com.example.testcontainers.testcontainers.entity.Consultant;
import com.example.testcontainers.testcontainers.model.ConsultantRequest;
import com.example.testcontainers.testcontainers.model.ConsultantResponse;
import com.example.testcontainers.testcontainers.repository.ConsultantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ConsultantServiceTest {

  @InjectMocks
  private ConsultantService service;
  @Mock
  private ConsultantRepository repository;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void shouldFindAllConsultants() {
    //Given
    when(repository.findAll()).thenReturn(List.of(mockConsultant("C1"), mockConsultant("C2")));

    //When
    Flux<ConsultantResponse> consultants = service.findAll();

    //Then
    StepVerifier.create(consultants)
        .assertNext(c -> {
          assertEquals("C1", c.getName());
        })
        .assertNext(c -> {
          assertEquals("C2", c.getName());
        })
        .verifyComplete();
  }

  @Test
  void shouldSaveConsultant() {
    //Given
    String consultantName = "SavedConsultant";
    when(repository.save(any(Consultant.class))).thenReturn(mockConsultant(consultantName));

    //When
    Mono<ConsultantResponse> saved = service.save(ConsultantRequest.builder()
        .name(consultantName)
        .technology("Java")
        .build());

    //Then
    StepVerifier.create(saved)
        .assertNext(c -> assertEquals(consultantName, c.getName()))
        .verifyComplete();
  }

  @Test
  void shouldDeleteConsultant() {
    //Given
    doNothing().when(repository).deleteById(any(UUID.class));

    //When
    service.delete(UUID.randomUUID().toString());

    //Verify
    verify(repository, atMostOnce()).deleteById(any(UUID.class));
  }

  private Consultant mockConsultant(String name) {
    return Consultant.builder()
        .id(UUID.randomUUID())
        .name(name)
        .build();
  }
}