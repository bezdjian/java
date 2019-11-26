package com.example.itemcatalogservice.repository;

import com.example.itemcatalogservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
// Endpoint localhost:8089/product from edge-server comes here because of @RepositoryRestResource!! AWSome!
public interface ProductRepository extends JpaRepository<Product, Long> {}
