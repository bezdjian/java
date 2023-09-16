package testcontainers.integration;

import testcontainers.config.ContainersConfig;
import testcontainers.entity.Consultant;
import testcontainers.model.BucketResponse;
import testcontainers.model.ConsultantRequest;
import testcontainers.model.ConsultantResponse;
import testcontainers.model.ConsultantsProjectResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import testcontainers.repository.ConsultantRepository;

// Todo: Important! Without config.ContainersConfig.class this test will target (application.yml) -> "prod".
@SpringBootTest(classes = {ContainersConfig.class})
class TestcontainersApplicationTests {

  private static final String BASE_URL = "/api";
  private static final String CONSULTANTS_URL = "/consultants";
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
  void shouldFetchBuckets() {
    webTestClient.get().uri(CONSULTANTS_URL + "/buckets")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(BucketResponse.class)
        .value(v -> v.forEach(System.out::println));
  }

  @Test
  void shouldFindAllConsultants() {
    webTestClient.get().uri(CONSULTANTS_URL)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(ConsultantResponse.class)
        .value(v -> v.forEach(System.out::println));
  }

  @Test
  void shouldSaveConsultant() {
    webTestClient.post().uri(CONSULTANTS_URL)
        .bodyValue(ConsultantRequest.builder()
            .name("Consultant 3")
            .technology("Java")
            .build())
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

  @Test
  @Disabled("This test communicates to another service test container which by default is disabled.")
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
