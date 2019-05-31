package com.sbab.test.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JourneyPatternPointOnLine {

    @JsonProperty(value = "StatusCode")
    private int statusCode;
    @JsonProperty(value = "Message")
    private String message;
    @JsonProperty(value = "ExecutionTime")
    private String executionTime;
    @JsonProperty(value = "ResponseData")
    private JourneyResponseData responseData;

    public JourneyPatternPointOnLine() {
    }

    public JourneyPatternPointOnLine(int statusCode, String message, String executionTime, JourneyResponseData responseData) {
        this.statusCode = statusCode;
        this.message = message;
        this.responseData = responseData;
        this.executionTime = executionTime;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JourneyResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(JourneyResponseData responseData) {
        this.responseData = responseData;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
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
