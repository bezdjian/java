package com.software.universe.reactive.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.software.universe.domain.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, ObjectId> {

}
