package testcontainers.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import testcontainers.entity.Consultant;
import testcontainers.model.ConsultantsProjectResponse;
import testcontainers.model.ProjectResponse;
import testcontainers.repository.ConsultantRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ProjectsClientServiceTest {

  @InjectMocks
  private ProjectsClientService service;
  @Mock
  private WebClient webClient;
  @Mock
  private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
  @Mock
  private WebClient.RequestHeadersSpec requestHeadersSpec;
  @Mock
  private WebClient.ResponseSpec responseSpec;
  @Mock
  private ConsultantRepository consultantRepository;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void findConsultantsByProjectTechnology() {
    //Given
    String technology = "Java";
    when(consultantRepository.findConsultantByTechnology(technology))
        .thenReturn(Flux.just(mockConsultant("C1", technology),
            mockConsultant("C2", "technology")));

    when(webClient.get()).thenReturn(requestHeadersUriSpec);
    when(requestHeadersUriSpec.uri(anyString(), any(Object.class)))
        .thenReturn(requestHeadersSpec);
    when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.bodyToFlux(ProjectResponse.class))
        .thenReturn(Flux.just(mockProjectResponse("P1", technology),
            mockProjectResponse("P1.1", technology),
            mockProjectResponse("P2", "anotherTech")));

    //When
    Flux<ConsultantsProjectResponse> result = service.findConsultantsByProjectTechnology(technology);

    StepVerifier.create(result)
        .assertNext(p -> assertEquals(technology, p.getTechnology()))
        .assertNext(p -> assertEquals(technology, p.getTechnology()))
        .verifyComplete();
  }

  private ProjectResponse mockProjectResponse(String name, String technology) {
    return ProjectResponse.builder()
        .uuid(UUID.randomUUID().toString())
        .description("Description")
        .name(name)
        .technology(technology)
        .build();
  }

  private Consultant mockConsultant(String name, String technology) {
    return Consultant.builder()
        .id(UUID.randomUUID())
        .technology(technology)
        .name(name)
        .build();
  }
}