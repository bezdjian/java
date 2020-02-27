package com.sbab.trafiklab.entity;

import com.sbab.trafiklab.dto.JourneyPatternPointOnLineResults;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "JourneyPoint")
@Getter
@Setter
@NoArgsConstructor
public class JourneyPointEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "lineNumber")
    private int lineNumber;
    @Column(name = "directionCode")
    private String directionCode;
    @Column(name = "journeyPatternPointNumber")
    private String journeyPatternPointNumber;
    @Column(name = "lastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime;
    @Column(name = "existsFromDate")
    private String existsFromDate;

    public JourneyPointEntity(int lineNumber, String directionCode, String journeyPatternPointNumber,
            String lastModifiedUtcDateTime, String existsFromDate) {
        this.lineNumber = lineNumber;
        this.directionCode = directionCode;
        this.journeyPatternPointNumber = journeyPatternPointNumber;
        this.lastModifiedUtcDateTime = lastModifiedUtcDateTime;
        this.existsFromDate = existsFromDate;
    }

    public JourneyPointEntity(JourneyPatternPointOnLineResults results) {
        this.lineNumber = results.getLineNumber();
        this.directionCode = results.getDirectionCode();
        this.journeyPatternPointNumber = results.getJourneyPatternPointNumber();
        this.lastModifiedUtcDateTime = results.getLastModifiedUtcDateTime();
        this.existsFromDate = results.getExistsFromDate();
    }
}
