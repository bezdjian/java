package com.example.testcontainers.testcontainers.repository;

import com.example.testcontainers.testcontainers.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, UUID> {

  List<Consultant> findConsultantsByTechnology(String technology);
}
