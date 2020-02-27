package com.sbab.trafiklab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonRootName(value = "ResponseData")
@Getter
@Setter
@NoArgsConstructor
public class StopPointResponseData {

    @JsonProperty(value = "Version")
    private String version;
    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "Result")
    private List<StopPointResults> result;

    public StopPointResponseData(List<StopPointResults> result, String version, String type) {
        this.result = result;
        this.version = version;
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
