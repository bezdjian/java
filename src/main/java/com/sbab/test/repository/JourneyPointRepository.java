package com.sbab.test.repository;

import com.sbab.test.dto.BussStopPointsDTO;
import com.sbab.test.entity.JourneyPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface JourneyPointRepository extends JpaRepository<JourneyPointEntity, Integer> {

    //Custom made queries
    @Query("select new com.sbab.test.dto.BussStopPointsDTO(j.lineNumber, s.stopPointName)" +
            " from JourneyPointEntity AS j " +
            " join StopPointEntity AS s on s.stopPointNumber = j.journeyPatternPointNumber" +
            " where j.lineNumber = :lineNumber and j.directionCode = 1" +
            " group by s.stopAreaNumber")
    List<BussStopPointsDTO> findStopsByLineNumber(int lineNumber);

    @Query("SELECT jp.lineNumber FROM JourneyPointEntity jp group by jp.lineNumber")
    List<Integer> getAllLineNumbers();
}