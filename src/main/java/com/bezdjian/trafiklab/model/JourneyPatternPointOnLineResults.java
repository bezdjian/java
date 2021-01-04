package com.bezdjian.trafiklab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JourneyPatternPointOnLineResults {

    //JourneyPoints attributes
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

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
