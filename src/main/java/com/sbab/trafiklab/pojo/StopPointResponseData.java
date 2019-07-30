package com.sbab.trafiklab.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@JsonRootName(value = "ResponseData")
public class StopPointResponseData {

    @JsonProperty(value = "Version")
    private String version;
    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "Result")
    private List<StopPointResults> result;

    public StopPointResponseData() {
        //Empty constructor for json-jackson
    }

    public StopPointResponseData(List<StopPointResults> result, String version, String type) {
        this.result = result;
        this.version = version;
        this.type = type;
    }

    public List<StopPointResults> getResult() {
        return result;
    }

    public void setResult(List<StopPointResults> result) {
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
