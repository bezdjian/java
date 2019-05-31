package com.sbab.test.repository;

import com.sbab.test.entity.StopPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopPointRepository extends JpaRepository<StopPointEntity, Integer> {
}
