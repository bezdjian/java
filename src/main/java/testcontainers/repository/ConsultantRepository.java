package testcontainers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testcontainers.entity.Consultant;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, UUID> {

  List<Consultant> findConsultantsByTechnology(String technology);

  List<Consultant> findConsultantByName(String name);
}
