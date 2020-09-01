package com.bezdjian.trafiklab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StopPointResults {

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

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
