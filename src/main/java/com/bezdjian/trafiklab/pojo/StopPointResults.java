package com.bezdjian.trafiklab.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopPointResults {

  //Stop points atttributes
  @JsonProperty(value = "StopPointNumber")
  private String stopPointNumber;
  @JsonProperty(value = "StopPointName")
  private String stopPointName;
  @JsonProperty(value = "StopAreaNumber")
  private String stopAreaNumber;
  @JsonProperty(value = "LocationNorthingCoordinate")
  private String locationNorthingCoordinate;
  @JsonProperty(value = "LocationEastingCoordinate")
  private String locationEastingCoordinate;
  @JsonProperty(value = "ZoneShortName")
  private String zoneShortName;
  @JsonProperty(value = "StopAreaTypeCode")
  private String stopAreaTypeCode;

  public StopPointResults() {
  }

  public StopPointResults(String stopPointNumber, String stopPointName, String stopAreaNumber, String locationNorthingCoordinate, String locationEastingCoordinate, String zoneShortName, String stopAreaTypeCode) {
    this.stopPointNumber = stopPointNumber;
    this.stopPointName = stopPointName;
    this.stopAreaNumber = stopAreaNumber;
    this.locationNorthingCoordinate = locationNorthingCoordinate;
    this.locationEastingCoordinate = locationEastingCoordinate;
    this.zoneShortName = zoneShortName;
    this.stopAreaTypeCode = stopAreaTypeCode;
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
