package com.sbab.trafiklab.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JourneyPatternPointOnLineResults {

    //JourneyPoints attrivutes
    @JsonProperty(value = "LineNumber")
    private int lineNumber;
    @JsonProperty(value = "DirectionCode")
    private String directionCode;
    @JsonProperty(value = "JourneyPatternPointNumber")
    private String journeyPatternPointNumber;
    @JsonProperty(value = "LastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime;
    @JsonProperty(value = "ExistsFromDate")
    private String existsFromDate;

    public JourneyPatternPointOnLineResults() {
    }

    public JourneyPatternPointOnLineResults(int lineNumber, String directionCode, String journeyPatternPointNumber, String lastModifiedUtcDateTime, String existsFromDate) {
        this.lineNumber = lineNumber;
        this.directionCode = directionCode;
        this.journeyPatternPointNumber = journeyPatternPointNumber;
        this.lastModifiedUtcDateTime = lastModifiedUtcDateTime;
        this.existsFromDate = existsFromDate;
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

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
