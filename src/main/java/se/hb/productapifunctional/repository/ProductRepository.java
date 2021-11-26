package se.hb.productapifunctional.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import se.hb.productapifunctional.model.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
