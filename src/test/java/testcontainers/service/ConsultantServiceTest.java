package testcontainers.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testcontainers.entity.Consultant;
import testcontainers.model.ConsultantRequest;
import testcontainers.model.ConsultantResponse;
import testcontainers.repository.ConsultantRepository;

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
    when(repository.findAll()).thenReturn(Flux.just(mockConsultant("C1"), mockConsultant("C2")));

    //When
    Flux<ConsultantResponse> consultants = service.findAll();

    //Then
    StepVerifier.create(consultants)
        .assertNext(c -> assertEquals("C1", c.getName()))
        .assertNext(c -> assertEquals("C2", c.getName()))
        .verifyComplete();
  }

  @Test
  void shouldSaveConsultant() {
    //Given
    String consultantName = "SavedConsultant";
    when(repository.save(any(Consultant.class))).thenReturn(Mono.just(mockConsultant(consultantName)));

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
    UUID uuid = UUID.randomUUID();
    when(repository.deleteById(eq(uuid))).thenReturn(Mono.empty());

    //When
    service.delete(uuid.toString());

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