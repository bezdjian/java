package com.software.universe.reactive.repository;

import com.software.universe.domain.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderRepository extends ReactiveMongoRepository<Order, ObjectId> {

}
