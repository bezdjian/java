package com.bezdjian.mylms.itemservice.repository;

import com.bezdjian.mylms.itemservice.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
// Endpoint localhost:8082/items from edge-server comes here because of @RepositoryRestResource!! AWSome!
public interface ItemRepository extends JpaRepository<Item, Long> {
}
