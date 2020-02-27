package com.bezdjian.trafiklab.repository;

import com.bezdjian.trafiklab.entity.StopPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopPointRepository extends JpaRepository<StopPointEntity, Integer> {
    // save method is being used in TrafikLabAPIService class.
}
