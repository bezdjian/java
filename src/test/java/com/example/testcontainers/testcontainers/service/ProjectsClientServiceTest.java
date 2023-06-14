package com.example.testcontainers.testcontainers.service;

import com.example.testcontainers.testcontainers.entity.Consultant;
import com.example.testcontainers.testcontainers.model.ConsultantsProjectResponse;
import com.example.testcontainers.testcontainers.model.ProjectResponse;
import com.example.testcontainers.testcontainers.repository.ConsultantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ProjectsClientServiceTest {

  @InjectMocks
  private ProjectsClientService service;
  @Mock
  private RestTemplate restTemplate;
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
    when(consultantRepository.findConsultantsByTechnology(technology))
        .thenReturn(List.of(mockConsultant("C1", technology),
            mockConsultant("C1", "technology")));

    when(restTemplate.exchange(eq("/projects/technology/"),
        eq(HttpMethod.GET),
        eq(null),
        eq(new ParameterizedTypeReference<List<ProjectResponse>>() {
        }),
        anyString()))
        .thenReturn(ResponseEntity.ok(
            List.of(mockProjectResponse("P1", technology),
                mockProjectResponse("P1.1", technology),
                mockProjectResponse("P2", "anotherTech"))));

    //When
    Flux<ConsultantsProjectResponse> result = service.findConsultantsByProjectTechnology(technology);

    StepVerifier.create(result)
        .assertNext(p -> {
          assertEquals(technology, p.getTechnology());
        })
        .assertNext(p -> {
          assertEquals(technology, p.getTechnology());
        })
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