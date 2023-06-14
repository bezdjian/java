package com.example.testcontainers.testcontainers.repository;

import com.example.testcontainers.testcontainers.entity.Consultant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest // This is isolated from spring boot, focuses only on JPA components.
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
class ConsultantRepositoryTest {

  @Autowired
  private ConsultantRepository consultantRepository;

  @Container
  //Todo: Since 3.1.0, @ServiceConnection automatically configures the necessary Spring Boot properties
  // for the supporting containers. No need for @DynamicPropertySource.
  //@ServiceConnection
  static MySQLContainer mySQLContainer = new MySQLContainer();

  // Todo: No need for @DynamicPropertySource when @ServiceConnection is used on the Container, yey!
  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mySQLContainer::getUsername);
    registry.add("spring.datasource.password", mySQLContainer::getPassword);
  }

  @Test
  void shouldSaveNewConsultant() {
    Consultant consultant1 = Consultant.builder()
        .id(UUID.randomUUID())
        .name("Consultant 1")
        .technology("Java")
        .build();
    Consultant consultant2 = Consultant.builder()
        .id(UUID.randomUUID())
        .name("Consultant 2")
        .technology("AWS")
        .build();
    consultantRepository.save(consultant1);
    consultantRepository.save(consultant2);

    List<Consultant> consultants = consultantRepository.findAll();
    assertEquals(2, consultants.size());
  }

  @Test
  void shouldDelete() {
    Consultant consultant = Consultant.builder()
        .id(UUID.randomUUID())
        .name("name")
        .build();

    UUID savedId = consultantRepository.save(consultant).getId();

    Optional<Consultant> consultants = consultantRepository.findById(savedId);

    assertFalse(consultants.isEmpty());
    assertEquals(consultants.get().getId(), savedId);
  }

  @Test
  void shouldFindByTechnology() {
    //Given
    Consultant consultant1 = Consultant.builder()
        .id(UUID.randomUUID())
        .name("Consultant 1")
        .technology("Java")
        .build();
    Consultant consultant2 = Consultant.builder()
        .id(UUID.randomUUID())
        .name("Consultant 2")
        .technology("AWS")
        .build();
    consultantRepository.save(consultant1);
    consultantRepository.save(consultant2);

    //When
    List<Consultant> consultants = consultantRepository.findConsultantsByTechnology("Java");

    assertFalse(consultants.isEmpty());
    assertEquals(1, consultants.size());
    assertEquals("Java", consultants.get(0).getTechnology());
  }
}