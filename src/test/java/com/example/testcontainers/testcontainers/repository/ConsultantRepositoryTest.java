package com.example.testcontainers.testcontainers.repository;

import com.example.testcontainers.testcontainers.ContainersConfig;
import com.example.testcontainers.testcontainers.entity.Consultant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // This is isolated from spring boot, focuses only on JPA components.
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
class ConsultantRepositoryTest {

  @Autowired
  private ConsultantRepository consultantRepository;

  @Container
  // Since 3.1.0, @ServiceConnection automatically configures the necessary Spring Boot properties
  // for the supporting containers. No need for @DynamicPropertySource.
  @ServiceConnection
  static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse(ContainersConfig.fullImageName))
      .withReuse(true);

  @Test
  void shouldSaveNewConsultant() {
    //Given
    String consultantName1 = "Consultant 1";
    Consultant consultant1 = Consultant.builder()
        .id(UUID.randomUUID())
        .name(consultantName1)
        .technology("Java")
        .build();

    String consultantName2 = "Consultant 2";
    Consultant consultant2 = Consultant.builder()
        .id(UUID.randomUUID())
        .name(consultantName2)
        .technology("AWS")
        .build();

    //When
    Consultant saved1 = consultantRepository.save(consultant1);
    Consultant saved2 = consultantRepository.save(consultant2);

    //Then
    Optional<Consultant> savedConsultant1 = consultantRepository.findById(saved1.getId());
    Optional<Consultant> savedConsultant2 = consultantRepository.findById(saved2.getId());

    assertTrue(savedConsultant1.isPresent());
    assertTrue(savedConsultant2.isPresent());
    assertEquals(consultantName1, savedConsultant1.get().getName());
    assertEquals(consultantName2, savedConsultant2.get().getName());
  }

  @Test
  void shouldDelete() {
    //Given
    Consultant consultant = Consultant.builder()
        .id(UUID.randomUUID())
        .name("Consultant to be deleted")
        .technology("Java")
        .build();

    UUID savedId = consultantRepository.save(consultant).getId();
    Optional<Consultant> consultants = consultantRepository.findById(savedId);

    assertFalse(consultants.isEmpty());
    assertEquals(consultants.get().getId(), savedId);

    //When
    consultantRepository.deleteById(savedId);
    Optional<Consultant> deletedConsultant = consultantRepository.findById(savedId);
    //Then
    assertTrue(deletedConsultant.isEmpty());
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
    assertEquals("Java", consultants.get(0).getTechnology());
  }
}