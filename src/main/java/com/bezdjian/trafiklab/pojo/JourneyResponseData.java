package com.bezdjian.trafiklab.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

  @Override
  public String toString() {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return e.getMessage();
    }
  }
}
