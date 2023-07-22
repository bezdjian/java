package com.example.testcontainers.testcontainers.integration;

import com.example.testcontainers.testcontainers.ContainersConfig;
import com.example.testcontainers.testcontainers.entity.Consultant;
import com.example.testcontainers.testcontainers.model.ConsultantResponse;
import com.example.testcontainers.testcontainers.model.ConsultantsProjectResponse;
import com.example.testcontainers.testcontainers.repository.ConsultantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

// Todo: Important! Without ContainersConfig.class this test will target (application.yml) -> "prod".
@SpringBootTest(classes = {ContainersConfig.class})
class TestcontainersApplicationTests {

  private static final String BASE_URL = "/api/consultants";
  private WebTestClient webTestClient;

  @Autowired
  private ConsultantRepository repository;

  @Autowired
  private ApplicationContext applicationContext;

  @BeforeEach
  void setup() {
    webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
        .configureClient()
        .baseUrl(BASE_URL)
        .build();

    createData();
  }

  @Test
  void shouldFindAllConsultants() {
    webTestClient.get().uri("/")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(ConsultantResponse.class)
        .value(v -> v.forEach(System.out::println));
  }

  @Test
  void shouldFindAllConsultantsByTechnology() {
    webTestClient.get().uri("/technology/Java")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(ConsultantResponse.class)
        .value(v -> v.forEach(System.out::println));
  }

  @Disabled("This test communicates to another service test container which by default is disabled.")
  @Test
  void shouldFindAllConsultantsByProjectTechnology() {
    webTestClient.get().uri("/projects/Java")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(ConsultantsProjectResponse.class)
        .value(v -> v.forEach(System.out::println));
  }

  private void createData() {
    Consultant consultant1 = Consultant.builder()
        .name("Consultant 1")
        .technology("Java")
        .build();
    Consultant consultant2 = Consultant.builder()
        .name("Consultant 2")
        .technology("AWS")
        .build();

    repository.save(consultant1);
    repository.save(consultant2);
  }
}
