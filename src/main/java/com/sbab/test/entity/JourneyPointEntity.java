package com.sbab.test.entity;

import com.sbab.test.pojo.JourneyPatternPointOnLineResults;

import javax.persistence.*;

@Entity
@Table(name = "JourneyPoint")
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

    public JourneyPointEntity(int lineNumber, String directionCode, String journeyPatternPointNumber, String lastModifiedUtcDateTime, String existsFromDate) {
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

    public JourneyPointEntity(){
        //Default no arguments constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDirectionCode() {
        return directionCode;
    }

    public void setDirectionCode(String directionCode) {
        this.directionCode = directionCode;
    }

    public String getJourneyPatternPointNumber() {
        return journeyPatternPointNumber;
    }

    public void setJourneyPatternPointNumber(String journeyPatternPointNumber) {
        this.journeyPatternPointNumber = journeyPatternPointNumber;
    }

    public String getLastModifiedUtcDateTime() {
        return lastModifiedUtcDateTime;
    }

    public void setLastModifiedUtcDateTime(String lastModifiedUtcDateTime) {
        this.lastModifiedUtcDateTime = lastModifiedUtcDateTime;
    }

    public String getExistsFromDate() {
        return existsFromDate;
    }

    public void setExistsFromDate(String existsFromDate) {
        this.existsFromDate = existsFromDate;
    }
}
