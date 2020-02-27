package com.bezdjian.trafiklab.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopPoint {

    @JsonProperty(value = "StatusCode")
    private int statusCode;
    @JsonProperty(value = "Message")
    private String message;
    @JsonProperty(value = "ExecutionTime")
    private String executionTime;
    @JsonProperty(value = "ResponseData")
    private StopPointResponseData responseData;

    public StopPoint() {
    }

    public StopPoint(int statusCode, String message, String executionTime, StopPointResponseData responseData) {
        this.statusCode = statusCode;
        this.message = message;
        this.responseData = responseData;
        this.executionTime = executionTime;
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
