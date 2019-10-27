package com.bezdjian.trafiklab.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    
  @Override
  public String toString() {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return e.getMessage();
    }
  }
}
