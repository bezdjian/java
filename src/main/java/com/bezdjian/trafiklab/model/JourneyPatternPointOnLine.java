package com.bezdjian.trafiklab.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JourneyPatternPointOnLine {

    @JsonProperty(value = "StatusCode")
    private int statusCode;
    @JsonProperty(value = "Message")
    private String message;
    @JsonProperty(value = "ExecutionTime")
    private String executionTime;
    @JsonProperty(value = "ResponseData")
    private JourneyResponseData responseData;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
