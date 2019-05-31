package com.sbab.test.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@JsonRootName(value = "ResponseData")
public class JourneyResponseData {

    @JsonProperty(value = "Version")
    private String version;
    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "Result")
    private List<JourneyPatternPointOnLineResults> result;

    public JourneyResponseData() {
        //Empty constructor for json-jackson
    }

    public JourneyResponseData(List<JourneyPatternPointOnLineResults> result, String version, String type) {
        this.result = result;
        this.version = version;
        this.type = type;
    }

    public List<JourneyPatternPointOnLineResults> getResult() {
        return result;
    }

    public void setResult(List<JourneyPatternPointOnLineResults> result) {
        this.result = result;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
