package testcontainers.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import testcontainers.entity.Consultant;

import java.util.UUID;

@Repository
public interface ConsultantRepository extends ReactiveMongoRepository<Consultant, UUID> {

  Flux<Consultant> findConsultantByTechnology(String technology);

  Flux<Consultant> findConsultantByName(String name);
}
