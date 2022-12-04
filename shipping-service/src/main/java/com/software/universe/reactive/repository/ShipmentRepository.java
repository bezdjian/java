package com.software.universe.reactive.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.software.universe.domain.Shipment;

public interface ShipmentRepository extends ReactiveMongoRepository<Shipment, ObjectId> {

}
